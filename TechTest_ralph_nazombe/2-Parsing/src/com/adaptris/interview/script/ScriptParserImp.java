package com.adaptris.interview.script;

import org.apache.oro.text.regex.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.oro.text.perl.Perl5Util;
import org.apache.oro.text.regex.MalformedPatternException;

public class ScriptParserImp implements ScriptParser {

    private File scriptFile;
    private List<String> scriptLines;

    public ScriptParserImp(File scriptFile) throws IOException {
        this.scriptFile = scriptFile;
        this.scriptLines = readScriptFile(scriptFile);
    }

    @Override
    public List<String> parse(String script) {
        List<String> resolvedLines = new ArrayList<>();

        try {
            Perl5Compiler compiler = new Perl5Compiler();
            PatternMatcher matcher = new Perl5Matcher();

            for (String line : scriptLines) {
                String resolvedLine = expandVariables(line);
                resolvedLines.add(resolvedLine);
            }
        } catch (MalformedPatternException | IOException e) {
            e.printStackTrace();
        }

        return resolvedLines;
    }

    @Override
    public String findArgument(String line, String[] qualifiers) throws IOException {
        for (String qualifier : qualifiers) {
            String argument = qualifier + " ";
            if (line.contains(argument)) {
                int startIndex = line.indexOf(argument) + argument.length();
                int endIndex = line.indexOf(" ", startIndex);
                if (endIndex == -1) {
                    endIndex = line.length();
                }
                return line.substring(startIndex, endIndex);
            }
        }
        return null;
    }

    @Override
    public String findVariableDefinition(String var) throws IOException, MalformedPatternException {
        Perl5Compiler compiler = new Perl5Compiler();
        PatternMatcher matcher = new Perl5Matcher();

        String variablePattern = "\\b" + var + "\\b.*?=.*?";

        Pattern pattern = compiler.compile(variablePattern, Perl5Compiler.READ_ONLY_MASK);
        for (String line : scriptLines) {
            if (matcher.contains(line, pattern)) {
                return line;
            }
        }
        return null;
    }

    @Override
    public String findMatch(String match) throws IOException, MalformedPatternException {
        for (String line : scriptLines) {
            if (line.contains(match)) {
                return line;
            }
        }
        return null;
    }

    @Override
    public String expandVariables(String line) throws MalformedPatternException, IOException {
        Perl5Util util = new Perl5Util();
        Perl5Compiler compiler = new Perl5Compiler();
        PatternMatcher matcher = new Perl5Matcher();

        String variablePattern = "\\$([^\\s]+)";

        Pattern pattern = compiler.compile(variablePattern, Perl5Compiler.READ_ONLY_MASK);
        PatternMatcherInput input = new PatternMatcherInput(line);
        StringBuffer buffer = new StringBuffer();

        while (matcher.contains(input, pattern)) {
            org.apache.oro.text.regex.MatchResult result = matcher.getMatch();
            String variable = result.group(1);
            String variableValue = findVariableDefinition(variable);
            if (variableValue != null) {
                matcher.appendReplacement(buffer, Matcher.quoteReplacement(variableValue));
            } else {
                // If variable definition not found, remove the variable from the line
                matcher.appendReplacement(buffer, "");
            }
        }
        matcher.appendTail(buffer);

        return buffer.toString();
    }

    private List<String> readScriptFile(File scriptFile) throws IOException {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(scriptFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }

        return lines;
    }
}
