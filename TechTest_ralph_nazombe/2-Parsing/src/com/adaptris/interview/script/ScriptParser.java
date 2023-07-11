/*
 *
 */

package com.adaptris.interview.script;
import java.io.IOException;
import java.util.List;

import org.apache.oro.text.regex.MalformedPatternException;

/**
 * <p>Consider the following
 * <code><pre>
 * JAR_DIR=/home/jar
 * APP_HOME=/home/lchan/app
 * JAR_LIB=$JAR_LIB:$JAR_DIR/log4j.jar
 * JAR_LIB=$JAR_LIB:$JAR_DIR/jdom.jar
 * JAR_LIB=$JAR_LIB:$JAR_DIR/lchan.jar
 * JAR_LIB=$JAR_LIB:$JAR_DIR/activation.jar
 * JAR_LIB=$JAR_LIB:$JAR_DIR/jakarta-oro-2.0.6.jar
 * JAR_LIB=$JAR_LIB:$APP_HOME/cfg
 * ....
 * ....
 * CLASSPATH=$APP_HOME:$JAR_LIB
 * ....
 * ....
 * java -classpath $CLASSPATH weblogic.Server
 * </pre></code>
 * <p>We need to parse it and resolve $CLASSPATH so that all referenced
 * variables are also resolved
 * </p>
 */
public interface ScriptParser {

  List<String> parse(String script);

  /** Find the argument that is specified by the qualifiers.
   *  <p>You might have input like
   *  <code><pre>
   *    java -cp $CLASSPATH someotherparameter weblogic.Server
   *  </pre></code>
   *  <p>This will return $CLASSPATH, given that String[] qualifiers={"cp"};
   *  @return the string found (or null if not found)
   *  @param line the line to parse
   *  @param qualifiers the list of qualifiers to match against.
   *  @throws IOException if you get one.
   */
  public String findArgument(String line, String[] qualifiers)  throws IOException;

  /** Find the line that assigns the supplied variable.
   *  <p>You have %CLASSPATH% as a variable returned from say, findArgument
   *  and you want to find the line that defines this variable...
   *  @param var the variable to find
   *  @return the definition of the variable, or null if the variable was not found
   */
  public String findVariableDefinition(String var) throws IOException, MalformedPatternException;
  
  /** Find the specified string in the file
   *  @param match the string to find
   *  @return the string that was found or null if not found.
   */  
  public String findMatch(String match)  throws IOException, MalformedPatternException;
  
  
  /** Expand the supplied line of shell variables
   * <code><pre>
   * $JAR_LIB:$JAR_DIR/log4j.jar
   * </pre></code>
   * <p>Will the expanded so that $JAR_LIB and $JAR_DIR are resolved.
   *  @param line the line
   *  @return the expanded line
   */
  public String expandVariables(String line)  throws MalformedPatternException, IOException;
}