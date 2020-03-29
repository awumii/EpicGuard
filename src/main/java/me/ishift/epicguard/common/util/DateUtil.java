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

package me.ishift.epicguard.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtil {
    /**
     * Time format is set to HH:mm:ss
     *
     * @return Current system time.
     */
    public static String getTime() {
        return format("HH:mm:ss");
    }

    /**
     * Date format is set to dd-M-yyyy
     *
     * @return Current system date.
     */
    public static String getDate() {
        return format("dd-M-yyyy");
    }

    /**
     * @param sdfValue Pattern for SimpleDateFormat.
     * @return Formatted String of SimpleDateFormat.
     */
    public static String format(String sdfValue) {
        final Calendar cal = Calendar.getInstance();
        final SimpleDateFormat sdf = new SimpleDateFormat(sdfValue);
        return sdf.format(cal.getTime());
    }
}
