package Models;

public class FarmersModel {
   String Id,Name,Location,Time,PictureUri,Phone,Email,Password,Designation;
   public  static final String KEY_COLLECTION_USER="Users";
   public  static final String KEY_DNAME="DName";
   public  static final String KEY_EMAIL="Email";
   public  static final String KEY_PAASSWORD="Password";
   public  static final String KEY_PREFERENCE_NAME="ChatAppPreferance";
   public  static final String KEY_IS_SIGNED_IN="IsSignedIn";
   public  static final String KEY_USERID="UserId";
   public  static final String KEY_IMAGE="Image";
   public  static final String KEY_PHONE_NUMBER="Phone";
   public  static final String KEY_FNAME="FName";
   public  static final String KEY_CNAME="CName";
   public  static final String KEY_PICTURE_URI="PictureUri";

   public  static final String KEY_DESIGNATION="Designation";
   public  static final String KEY_FCM="fcmToken";
   public  static final String KEY_USER="EachUser";

   public  static final String KEY_COLLECTION_CHAT="CHAT";
   public  static final String KEY_SENDER_ID="senderId";
   public  static final String KEY_RECEIVER_ID="receiverId";
   public  static final String KEY_message="message";
   public  static final String KEY_TIMESTAMP="timestamp";
   public static final String KEY_COLLECTION_CONVERSATIONS = "conversations";
   public static final String KEY_SENDER_NAME = "senderName";
   public static final String KEY_RECEIVER_NAME = "receiverName";
   public static final String KEY_SENDER_IMAGE ="senderImage";
   public static final String KEY_RECEIVER_IMAGE = "receiverImage";
   public static final String KEY_LAST_MESSAGE= "lastMessage";

   public static final String KEY_ITEM_NAME= "itemName";
   public static final String KEY_ITEM_DESCRIPTION= "itemDescription";
   public static final String KEY_ITEM_PICTURE= "itemPicture";
   public static final String KEY_ITEM_PRICE= "itemPrice";
   public static final String KEY_FARMER_LOCATION= "farmerLocation";
   public static final String KEY_ITEM_STATUS= "itemStatus";


   public static final String KEY_AVAILABILITY = "availability";









    public FarmersModel() {
    }

    public String getPictureUri() {
        return PictureUri;
    }

    public void setPictureUri(String pictureUri) {
        PictureUri = pictureUri;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }


    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }
}
