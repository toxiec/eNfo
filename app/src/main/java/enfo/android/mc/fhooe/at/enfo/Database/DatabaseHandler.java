package enfo.android.mc.fhooe.at.enfo.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import enfo.android.mc.fhooe.at.enfo.Entities.Tournament;


public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHandler";
    private static int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FAVORITES_DB";
    private static final String TABLE_NAME = "FAVORITES_TABLE";
    private static String DB_FULL_PATH = "//data/data/enfo.android.mc.fhooe.at.enfo/databases/" + DATABASE_NAME;

    public static final String KEY_ID = "id";
    public static final String KEY_DISCIPLINE = "discipline";
    public static final String KEY_NAME = "name";
    public static final String KEY_FULLNAME = "fullname";
    public static final String KEY_DATE_START = "date_start";
    public static final String KEY_DATE_END = "date_end";
    public static final String KEY_ONLINE = "online";
    public static final String KEY_PUBLIC = "public";
    public static final String KEY_LOCATION = "location";
    public static final String KEY_COUNTRY = "country";
    public static final String KEY_SIZE = "size";

    public static Context mContext;

    public DatabaseHandler(Context _context) {
        super(_context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = _context;
        //super(mContext, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_DISCIPLINE + " TEXT, "
                + KEY_NAME + " TEXT, "
                + KEY_FULLNAME + " TEXT, "
                + KEY_DATE_START + " TEXT, "
                + KEY_DATE_END + " TEXT, "
                + KEY_ONLINE + " INTEGER, "
                + KEY_PUBLIC + " INTEGER, "
                + KEY_LOCATION + " TEXT, "
                + KEY_COUNTRY + " TEXT, "
                + KEY_SIZE + " INTEGER"
                + ");";


        System.out.println("---create table" + CREATE_TABLE);
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    /**
     * This method is used to add Tournaments to the database.
     *
     * @param _tournament This is the Tournament Object that should be inserted into the database.
     *
     */
    public void addTournament(Tournament _tournament) {
        Log.wtf("ck", " in addArticle");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_ID, _tournament.getmID());
        values.put(KEY_DISCIPLINE, _tournament.getmDiscipline());
        values.put(KEY_NAME, _tournament.getmName());
        values.put(KEY_FULLNAME, _tournament.getmFullName());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date tournamentDateStart = _tournament.getmDateStart();
        Date tournamentDateEnd = _tournament.getmDateEnd();
        String dateStart = sdf.format(tournamentDateStart);
        String dateEnd = sdf.format(tournamentDateEnd);
        values.put(KEY_DATE_START, dateStart);
        values.put(KEY_DATE_END, dateEnd);

        boolean online = _tournament.ismOnline();
        int isOnline = (online)? 1 : 0;
        values.put(KEY_ONLINE, isOnline);

        int isPublic = (_tournament.ismPublic()) ? 1 : 0;
        values.put(KEY_PUBLIC, isPublic);

        values.put(KEY_LOCATION, _tournament.getmLocation());
        values.put(KEY_COUNTRY, _tournament.getmCountry());

        values.put(KEY_SIZE, _tournament.getmSize());
        // Inserting Row
        long insert = db.insert(TABLE_NAME, null, values);
        if(insert == -1){
            Log.i(TAG,"Not inserted correctly");
        }else{
            Log.i(TAG,"Inserted correctly");;
        }
        db.close(); // Closing database connection
    }

    /**
     *  This method clears the entire Database. (Is never used in the program)
     *
     * @param context Context of the DatabaseHandler; is used to generate new Instance of the databasehandler
     */
    public static void clearDatabase(Context context) {
        Log.d("logtag", "in clearDatabase");
        DatabaseHandler helper = new DatabaseHandler(context);
        SQLiteDatabase database = helper.getWritableDatabase();
        database.delete(TABLE_NAME, null, null); //erases everything in the table.
        helper.close();
        database.close();
        Log.d("logtag", "Database cleared");
    }

    /**
     *  This method is used to get a specific tournament from the local database. If there is no tournament with
     *  this id, it returns a tournament with the value null.
     *
     * @param id The id(String) from the tournament is used to get the specific tournament from the database
     * @return  returns the specific tournament. if it's not found it returns a tournament with the value null.
     */
    public static Tournament getTournament(String id) {

        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_ID + " IS " + "\"" + id + "\""
                + " LIMIT " + 1 + ";";
        DatabaseHandler helper = new DatabaseHandler(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        db.close();

        Tournament tournament = new Tournament();

        if (cursor.moveToFirst()) {
            tournament.setmID(cursor.getString(0));
            tournament.setmDiscipline(cursor.getString(1));
            tournament.setmName(cursor.getString(2));
            tournament.setmFullName(cursor.getString(3));

            String dateStart = cursor.getString(4);
            String dateEnd = cursor.getString(5);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date_start = new Date();
            Date date_end = new Date();
            try {
                date_start = sdf.parse(dateStart);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                date_end = sdf.parse(dateEnd);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            tournament.setmDateStart(date_start);
            tournament.setmDateEnd(date_end);

            boolean isOnline = cursor.getInt(6) > 0;
            tournament.setmOnline(isOnline);
            boolean isPublic = cursor.getInt(7) > 0;
            tournament.setmPublic(isPublic);
            tournament.setmLocation(cursor.getString(8));
            tournament.setmCountry(cursor.getString(9));
            tournament.setmSize(cursor.getInt(10));

        } else {
            tournament = null;
        }
        db.close();
        return tournament;
    }


    /**
     * This method returns a list with all tournaments stored in database.
     *
     * @return list with all tournaments stored in database
     */
    public static List<Tournament> getAllEntries() {

        List<Tournament> favoriteList = new ArrayList<Tournament>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        DatabaseHandler helper = new DatabaseHandler(mContext);

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Tournament tournament = new Tournament();

                tournament.setmID(cursor.getString(0));
                tournament.setmDiscipline(cursor.getString(1));
                tournament.setmName(cursor.getString(2));
                tournament.setmFullName(cursor.getString(3));

                String dateStart = cursor.getString(4);
                String dateEnd = cursor.getString(5);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date_start = new Date();
                Date date_end = new Date();
                try {
                    date_start = sdf.parse(dateStart);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                try {
                    date_end = sdf.parse(dateEnd);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                tournament.setmDateStart(date_start);
                tournament.setmDateEnd(date_end);

                boolean isOnline = cursor.getInt(6) > 0;
                tournament.setmOnline(isOnline);
                boolean isPublic = cursor.getInt(7) > 0;
                tournament.setmPublic(isPublic);
                tournament.setmLocation(cursor.getString(8));
                tournament.setmCountry(cursor.getString(9));
                tournament.setmSize(cursor.getInt(10));

                // Adding tournament to list
                favoriteList.add(tournament);
            } while (cursor.moveToNext());
        }
        db.close();
        // return contact list
        return favoriteList;
    }

    /**
     * This method checks if the database exists.
     * @return boolean; is true if database exists, is false if database is not existing
     */
    public static boolean checkIfDataBaseExists() {
        Boolean dbExists = false;
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(DB_FULL_PATH, null,
                    SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
            dbExists = true;
            Log.d("logtag", " Datenbank existiert");
            return dbExists;


        } catch (SQLiteException e) {
            // database doesn't exist yet.
            Log.d("logtag", " Datenbank existiert nicht");
            dbExists = false;
            return dbExists;

        }
    }


    /**
     * This method deletes a specific row.
     *
     * @param id The id(String) of the tournament that has to be deleted.
     */
    public void deleteRow(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + KEY_ID + " IS " + id);
        db.close();
    }

    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "message" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){
            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }
    }


}
