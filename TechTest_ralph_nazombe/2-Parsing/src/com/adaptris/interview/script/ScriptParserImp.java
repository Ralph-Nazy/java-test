package com.adaptris.interview.script;

import org.apache.oro.text.regex.MalformedPatternException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScriptParserImp implements ScriptParser {


    public ScriptParserImp(File file) {
    }

    @Override
    public List<String> parse(String script) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(script))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line into the file path and variable name
                String[] parts = line.split(":");
                String filePath = parts[0].trim();
                String variableName = parts[1].trim();

                // Add the file path and variable name to the list of variables
                lines.add(filePath + ":" + variableName);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return lines;
    }

    @Override
    public String findArgument(String line, String[] qualifiers) throws IOException {
        return null;
    }

    @Override
    public String findVariableDefinition(String var) throws IOException, MalformedPatternException {
        return null;
    }

    @Override
    public String findMatch(String match) throws IOException, MalformedPatternException {
        return null;
    }

    @Override
    public String expandVariables(String line) throws MalformedPatternException, IOException {
        return null;
    }
}