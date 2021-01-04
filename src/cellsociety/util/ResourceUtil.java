package cellsociety.util;

import java.util.ResourceBundle;

/**
 * Used to access the particular Resources required for the program based on a language
 */
public final class ResourceUtil {

  private static ResourceBundle resourceBundle;

  private ResourceUtil() {
    //not called but need a private constructor since it is a util class
  }

  /**
   * gets a value based on an input key that searches a particular ResourceBundle
   *
   * @param key the key used to access the value in the properties file
   * @return the value called for by the key
   */
  public static String getResourceValue(String key) {
    return resourceBundle.getString(key);
  }

  /**
   * sets the language by which the resource bundle is updated
   *
   * @param language the language passed
   */
  public static void setLanguage(String language) {
    ResourceUtil.resourceBundle = ResourceBundle.getBundle(language);
  }
}

