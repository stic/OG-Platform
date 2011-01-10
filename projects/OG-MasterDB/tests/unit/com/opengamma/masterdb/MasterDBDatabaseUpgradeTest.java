/**
 * Copyright (C) 2009 - Present by OpenGamma Inc.
 *
 * Please see distribution for license.
 */
package com.opengamma.masterdb;

import com.opengamma.util.test.DBUpgradeTest;

/**
 * Tests the database upgrade scripts.
 */
public class MasterDBDatabaseUpgradeTest extends DBUpgradeTest {

  public MasterDBDatabaseUpgradeTest(final String databaseType, final String databaseVersion) {
    super(databaseType, databaseVersion);
  }

}
