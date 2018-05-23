package TT.domain.project;

public class Image {

  private int imageNum;
  private String imagePath;
  private String author;

  public Image(String imagePath, String author) {
    this.imagePath = imagePath;
    this.author = author;
  }

  public int getImageNum() {
    return imageNum;
  }

  public void setImageNum(int imageNum) {
    this.imageNum = imageNum;
  }

  public String getImagePath() {
    return imagePath;
  }

  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  @Override
  public String toString() {
    return "Image [imageNum=" + imageNum + ", imagePath=" + imagePath + ", author=" + author + "]";
  }
}
