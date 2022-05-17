package susmanager;

public class searchPwd {

  int NumberOfPwd;
  String Website;

  public searchPwd(int NumberOfPwdInp, String WebsiteInp) {
    this.NumberOfPwd = NumberOfPwdInp;
    this.Website = WebsiteInp;
  }

  public int getNumberOfPwd() {
    return NumberOfPwd;
  }

  public String getWebsite() {
    return Website;
  }
}
