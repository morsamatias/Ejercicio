package ejercicio.matiasmorsa.Item;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class Item {


    public Item(){}

    //// VARIABLES
    private static final String USED_SPANISH = "Usado";
    private static final String NEW_SPANISH = "Nuevo";
    private static final String TRUE_SPANISH = "Si";
    private static final String FALSE_SPANISH = "No";
    private String brand = "-1";
    private String city = "-1" ;
    private String condition = "-1";
    private String id = "-1";
    private String imageURL = "-1";
    private String mercadopago = "-1";
    private String permalink = "-1";
    private String price = "-1";
    private String quantity = "-1";
    private String shipping = "-1";
    private String title = "-1";

    // GETTERS Y SETTERS
    public void setPrice(String _price) {
        price = "$" + _price;
    }
    public void setId(String _id) {
        id = validateString(_id);
    }
    public void setCity(String _city) { city = validateString(_city); }
    public void setTitle(String _title) {
        title = validateString(_title);
    }
    public void setImageURL(String _imagurl){ imageURL = _imagurl; }
    public void setCondition(String _condition) {
        if(_condition.equals("used")){
            condition = USED_SPANISH;
        }else{
            condition = NEW_SPANISH;}
    }
    public void setQuantity(String _quantity) { quantity = _quantity; }


    public String getImageURL(){
           return imageURL;
        }
    public String getPrice() { return price ; }
    public String getCity() { return city; }
    public String getId() { return String.valueOf(id); }
    public String getCondition() {
        return condition;
    }
    public String getTitle() {
        return title;
    }
    public String getQuantity() {
        return quantity;
    }
    public String getBrand() { return brand;}
    public String getPermalink() { return permalink;}
    public String getShipping() {
        return shipping;
    }

    public void setBrand(String _brand) {
        brand = _brand;
    }

    private String validateString(String _string){
        if(_string.length() > 31) {
            return (_string.substring(0, 30) + "...");
        }else{
            return _string;
        }
    }
    public Bitmap getImageBitmap() {
        Bitmap bm = null;
        try {
            URL aURL = new URL(imageURL);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.e("Error", "Error getting bitmap", e);
        }
        return bm;
    }

    public Bitmap getImageBitmapScaled(){
        Bitmap bitmapImage = this.getImageBitmap();
        int nh = (int) ( bitmapImage.getHeight() * (256.0 / bitmapImage.getWidth()) );
        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 256, nh, true);
        return scaled;
    }

    public Bitmap getImageBitmapScaledMini(){
        Bitmap bitmapImage = this.getImageBitmap();
        int nh = (int) ( bitmapImage.getHeight() * (128.0 / bitmapImage.getWidth()) );
        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 128, nh, true);
        return scaled;
    }


    public void setShipping(String _shipping) {
        if(_shipping.equals(TRUE_SPANISH)){
            shipping = TRUE_SPANISH;
        }else{
            shipping = FALSE_SPANISH;
        }
    }

    public void setMercadopago(String _accepts_mercadopago) {
        mercadopago = _accepts_mercadopago;
    }
    public void setPermalink(String _permalink){
        permalink = _permalink;
    }


}
