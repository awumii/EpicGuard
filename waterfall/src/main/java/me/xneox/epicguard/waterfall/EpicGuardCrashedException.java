package me.xneox.epicguard.waterfall;

public class EpicGuardCrashedException extends RuntimeException {
  public EpicGuardCrashedException() {
    super("A critical error has occurred in EpicGuard that prevented it from further loading. Look for errors above.");
  }
}
