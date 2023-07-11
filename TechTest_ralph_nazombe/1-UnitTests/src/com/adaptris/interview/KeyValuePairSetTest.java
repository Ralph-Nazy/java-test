/*
 * $RCSfile: KeyValuePairSetTest.java,v $
 * $Revision: 1.1 $
 * $Date: 2006/05/18 22:49:58 $
 * $Author: hfraser $
 */
package com.adaptris.interview;

import junit.framework.TestCase;
import org.junit.Test;

public class KeyValuePairSetTest extends TestCase {

  private KeyValuePairSet kvps;
  public KeyValuePairSetTest(String arg0) {
    super(arg0);
  }

  protected void setUp() throws Exception {
    kvps = new KeyValuePairSet();
    kvps.addKeyValuePair(new KeyValuePair("key", "val"));
  }


  public void testContains() {
    assertTrue(kvps.contains(new KeyValuePair("key", "val")));
  }

  public void testGetValue() {
    assertEquals(kvps.getKeyValuePair("val"), null);
  }

  @Test
  public void testRemove() {
    kvps.removeKeyValuePair(new KeyValuePair("key", "val"));
    assertEquals(0, kvps.size());
  }


}
