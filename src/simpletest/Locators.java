package simpletest;

public class Locators extends AndroidTest {
// Authentication page
   public static String  _btnSignIn = ById("growth_prereg_fragment_sign_in_button");

   // User name and password
   public static String  _txtEmail = ById("growth_login_join_fragment_email_address");
   public static String  _txtPasswd = ById("growth_login_join_fragment_password");

   // Login Submit
   public static String  _btnLoginSubmit = ById("growth_login_fragment_sign_in");

   public static String getNavTab(int id){
      return String.format("//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.support.v4.widget.DrawerLayout[1]/android.widget.LinearLayout[1]/android.widget.LinearLayout[1]/android.widget.LinearLayout[1]/android.widget.HorizontalScrollView[1]/android.widget.LinearLayout[1]/android.support.v7.app.ActionBar.Tab[%d]", id);
   }

  public static String ById(String id) {
    return AndroidTest._appPackage + ":id/" + id;
  }
}

