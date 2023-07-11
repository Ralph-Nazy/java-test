/*
 *
 */
package com.adaptris.interview.script;
import java.io.File;

public class Main {

  public static void main(String[] argv)  throws Exception {
    ScriptParser p = new ScriptParserImp(new File("script.sh"));
    String[] cpQualifiers = {
      "classpath", 
      "cp"
    };
    String line = p.findMatch("weblogic.Server");
    String classpath = p.findArgument(line, cpQualifiers);
    String def = p.findVariableDefinition(classpath);
    System.out.println("Expanded line: " + p.expandVariables(def));
  }
}