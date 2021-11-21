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

package me.xneox.epicguard.core.storage;

import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

/**
 * AddressMeta holds information about an IP address.
 * All known AddressMeta's are stored in the database and cached in the {@link StorageManager}
 */
public class AddressMeta {
  private final List<String> nicknames;
  private boolean blacklisted;
  private boolean whitelisted;

  public AddressMeta(boolean blacklisted, boolean whitelisted, @NotNull List<String> nicknames) {
    this.blacklisted = blacklisted;
    this.whitelisted = whitelisted;
    this.nicknames = nicknames;
  }

  public boolean blacklisted() {
    return this.blacklisted;
  }

  public void blacklisted(boolean blacklisted) {
    this.blacklisted = blacklisted;
  }

  public boolean whitelisted() {
    return this.whitelisted;
  }

  public void whitelisted(boolean whitelisted) {
    this.whitelisted = whitelisted;
  }

  @NotNull
  public List<String> nicknames() {
    return this.nicknames;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddressMeta that = (AddressMeta) o;
    return blacklisted == that.blacklisted && whitelisted == that.whitelisted
        && Objects.equals(nicknames, that.nicknames);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nicknames, blacklisted, whitelisted);
  }
}
