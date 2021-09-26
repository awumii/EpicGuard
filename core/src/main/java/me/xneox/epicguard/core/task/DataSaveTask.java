/*
 * EpicGuard is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EpicGuard is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package me.xneox.epicguard.core.task;

import java.sql.SQLException;
import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.util.LogUtils;

/**
 * This task saves data to the database.
 */
public class DataSaveTask implements Runnable {
  private final EpicGuard epicGuard;

  public DataSaveTask(EpicGuard epicGuard) {
    this.epicGuard = epicGuard;
  }

  @Override
  public void run() {
    try {
      this.epicGuard.storageManager().database().saveData();
    } catch (SQLException exception) {
      LogUtils.catchException("Could not save data to the SQL database (save-task)", exception, false);
    }
  }
}
