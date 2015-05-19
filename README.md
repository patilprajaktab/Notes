# Notes
The app has 3 activities:

1. List Activity
The list activity  display the list of saved notes. Each row is represented with a simple of piece of text (a simple ListAdapter).

The ActionBar has a button for adding a new photo, which launches the "Add Photo Activity" (described below).

2. View Photo Activity
This activity is simple: it displays the photo and the caption that was clicked on in the List Activity.

3. Add Photo Activity
This activity should has 3 fields:
EditText field for the caption
Button for taking the photo. This should launch the camera intent.

This app uses SQLite. Table has two fields:
Caption field - This is a user-specified text field.
Path field - This holds the absolute path to the photo.

Directory is created in the Data Storage of phone, which Stores the Images clciked using the app.
If the app is uninstalled the Directory is deleted automatically.
![alt tag](http://https://mail-attachment.googleusercontent.com/attachment/u/0/?ui=2&ik=e601d5aa19&view=att&th=14d5f2ee70d2f299&attid=0.12&disp=safe&realattid=1501373111731224576-local11&zw&saduie=AG9B_P-WLI7eCkvR2AN8blnWmODo&sadet=1431821098426&sadat=ANGjdJ_qHZuLbrOKkqEKKgo4s-fFum0NfMxWNP3iIWqRARXjj9M7DYjbL7_1OmM&sads=LKEqPvJTP_bR8N0ZNklOXgmZ0jc, https://mail.google.com/mail/u/0/Screenshot_2015-05-16-16-59-42.png)
