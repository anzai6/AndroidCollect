package com.android.collect.library.db;

/**
 * 埋点信息的表
 *
 * @author anzai
 */
public class RecordDataTable {

    /*private static final String TAG = LogUtils.getLogTag(RecordDataTable.class);

    private static final String RECORD_DATA_TABLE_NAME = "record_data_table";

    private static final String ID = "_id";
    private static final String EVENT = "event"; // 事件类型：Page和Action
    private static final String ACTION_ID = "action_id"; // 交易名
    private static final String PAGE_NAME = "page_name"; // 页面名称
    private static final String TIME_IN = "time_in"; // 开始时间
    private static final String TIME_OUT = "time_out"; // 结束时间
    private static final String USER_ID = "user_id"; // 用户ID
    private static final String DEVICE_MODEL = "device_model"; // 手机型号，如：iPhone 8Plus或小米6
    private static final String DEVICE_TYPE = "device_type"; // 设备类型：Android和iOS
    private static final String DEVICE_OS_TYPE = "device_os_type"; // 系统版本，如：Android 4.4或iOS8
    private static final String VERSION_CODE = "version_code"; // 客户端版本号
    private static final String TRADE_STATU = "trade_statu"; // 交易成功或者失败，Succeed/Failed

    // 创建表语句
    public static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS "
            + RECORD_DATA_TABLE_NAME + "(" + ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + EVENT
            + " TEXT NOT NULL," + ACTION_ID + " TEXT," + PAGE_NAME
            + " TEXT," + TIME_IN + " TEXT," + TIME_OUT + " TEXT," + USER_ID + " TEXT,"
            + DEVICE_MODEL + " TEXT NOT NULL," + DEVICE_TYPE + " TEXT NOT NULL,"
            + DEVICE_OS_TYPE + " TEXT NOT NULL," + VERSION_CODE + " TEXT NOT NULL,"
            + TRADE_STATU + " TEXT)";

    // 删除表格语句
    public static final String DROP_TABLE_SQL = "DROP TABLE IF EXISTS " + RECORD_DATA_TABLE_NAME;

    *//**
     * 传入列表添加入数据库
     *//*
    public static void addRecordDataDetailList(Context context, List<RecordDataDetail> list) {

        SQLiteDatabase db = AppSqlHelper.getInstance(context)
                .getWritableDatabase();
        db.beginTransaction();
        try {
            for (RecordDataDetail detail : list) {
                db.execSQL(
                        "insert into record_data_table values(null,?,?,?,?,?,?,?,?,?,?,?)",
                        new Object[]{detail.getEvent(),
                                detail.getActionId(),
                                detail.getPageName(), detail.getTimeIn(),
                                detail.getTimeOut(), detail.getUserId(), detail.getDeviceModel(),
                                detail.getDeviceType(), detail.getDeviceOsType(),
                                detail.getVersionCode(), detail.getTradeStatu()});
            }
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }

    }

    *//**
     * 传入文件详情添加入数据库
     *//*
    public static long addRecordDataDetail(Context context, RecordDataDetail detail) {

        long id = -1;
        SQLiteDatabase db = AppSqlHelper.getInstance(context)
                .getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues cv = new ContentValues();
            cv.put(EVENT, detail.getEvent());
            cv.put(ACTION_ID, detail.getActionId());
            cv.put(PAGE_NAME, detail.getPageName());
            cv.put(TIME_IN, detail.getTimeIn());
            cv.put(TIME_OUT, detail.getTimeOut());
            cv.put(USER_ID, detail.getUserId());
            cv.put(DEVICE_MODEL, detail.getDeviceModel());
            cv.put(DEVICE_TYPE, detail.getDeviceType());
            cv.put(DEVICE_OS_TYPE, detail.getDeviceOsType());
            cv.put(VERSION_CODE, detail.getVersionCode());
            cv.put(TRADE_STATU, detail.getTradeStatu());
            id = db.insert(RECORD_DATA_TABLE_NAME, "", cv);
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return id;

    }

    *//**
     * 根据ID更新RecordDataDetail的TimeOut和tradeState
     *//*
    public static int updatePartRecordDataDetail(Context context, long id, String timeOut, String tradeState) {

        int result_count = 0;
        SQLiteDatabase db = AppSqlHelper.getInstance(context)
                .getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues cv = new ContentValues();
            cv.put(TIME_OUT, timeOut);
            cv.put(TRADE_STATU, tradeState);
            String[] args = {id + ""};
            result_count = db.update(RECORD_DATA_TABLE_NAME, cv, ID
                    + "=?", args);

            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }

        return result_count;

    }

    *//**
     * 根据ID更新RecordDataDetail数据库
     *//*
    public static int updateRecordDataDetail(Context context, RecordDataDetail detail) {

        int result_count = 0;
        SQLiteDatabase db = AppSqlHelper.getInstance(context)
                .getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues cv = new ContentValues();
            cv.put(EVENT, detail.getEvent());
            cv.put(ACTION_ID, detail.getActionId());
            cv.put(PAGE_NAME, detail.getPageName());
            cv.put(TIME_IN, detail.getTimeIn());
            cv.put(TIME_OUT, detail.getTimeOut());
            cv.put(USER_ID, detail.getUserId());
            cv.put(DEVICE_MODEL, detail.getDeviceModel());
            cv.put(DEVICE_TYPE, detail.getDeviceType());
            cv.put(DEVICE_OS_TYPE, detail.getDeviceOsType());
            cv.put(VERSION_CODE, detail.getVersionCode());
            cv.put(TRADE_STATU, detail.getTradeStatu());
            String[] args = {detail.getId()};
            result_count = db.update(RECORD_DATA_TABLE_NAME, cv, ID
                    + "=?", args);

            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }

        return result_count;

    }

    *//**
     * 根据id删除菜单项
     *//*
    public static void deleteRecordDataById(Context context, String id) {
        SQLiteDatabase db = AppSqlHelper.getInstance(context)
                .getWritableDatabase();
        db.beginTransaction();
        try {
            db.execSQL("delete from record_data_table where _id = ?",
                    new Object[]{Integer.parseInt(id)});
            db.setTransactionSuccessful();
            LogUtils.v(TAG, "delete RecordData success where _id = " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    *//**
     * 查询数据库的所有菜单项列表
     *//*
    public static List<RecordDataDetail> findAllRecordDataDetail(
            Context context) {

        SQLiteDatabase db = AppSqlHelper.getInstance(context)
                .getWritableDatabase();
        ArrayList<RecordDataDetail> items = null;
        Cursor c = db.rawQuery("select * from record_data_table ", null);
        while (c.moveToNext()) {
            if (items == null)
                items = new ArrayList<RecordDataDetail>();
            RecordDataDetail item = new RecordDataDetail();
            item.setId(c.getInt(c.getColumnIndex(ID)) + "");
            item.setEvent(c.getString(c.getColumnIndex(EVENT)));
            item.setActionId(c.getString(c.getColumnIndex(ACTION_ID)));
            item.setPageName(c.getString(c.getColumnIndex(PAGE_NAME)));
            item.setTimeIn(c.getString(c.getColumnIndex(TIME_IN)));
            item.setTimeOut(c.getString(c.getColumnIndex(TIME_OUT)));
            item.setUserId(c.getString(c.getColumnIndex(USER_ID)));
            item.setDeviceModel(c.getString(c.getColumnIndex(DEVICE_MODEL)));
            item.setDeviceType(c.getString(c.getColumnIndex(DEVICE_TYPE)));
            item.setDeviceOsType(c.getString(c.getColumnIndex(DEVICE_OS_TYPE)));
            item.setVersionCode(c.getString(c.getColumnIndex(VERSION_CODE)));
            item.setTradeStatu(c.getString(c.getColumnIndex(TRADE_STATU)));
            items.add(item);
        }
        c.close();

        return items;
    }

    *//**
     * 通过Event查询数据库中相应的前50条记录
     *//*
    public static List<RecordDataDetail> findRecordDataDetailByEvent(
            Context context, String event) {
        SQLiteDatabase db = AppSqlHelper.getInstance(context)
                .getWritableDatabase();
        ArrayList<RecordDataDetail> items = null;
        Cursor c = db.rawQuery(
                "select * from record_data_table where event = '"
                        + event + "'", null);
        while (c.moveToNext()) {
            if (items == null)
                items = new ArrayList<RecordDataDetail>();
            RecordDataDetail item = new RecordDataDetail();
            item.setId(c.getInt(c.getColumnIndex(ID)) + "");
            item.setEvent(c.getString(c.getColumnIndex(EVENT)));
            item.setActionId(c.getString(c.getColumnIndex(ACTION_ID)));
            item.setPageName(c.getString(c.getColumnIndex(PAGE_NAME)));
            item.setTimeIn(c.getString(c.getColumnIndex(TIME_IN)));
            item.setTimeOut(c.getString(c.getColumnIndex(TIME_OUT)));
            item.setUserId(c.getString(c.getColumnIndex(USER_ID)));
            item.setDeviceModel(c.getString(c.getColumnIndex(DEVICE_MODEL)));
            item.setDeviceType(c.getString(c.getColumnIndex(DEVICE_TYPE)));
            item.setDeviceOsType(c.getString(c.getColumnIndex(DEVICE_OS_TYPE)));
            item.setVersionCode(c.getString(c.getColumnIndex(VERSION_CODE)));
            item.setTradeStatu(c.getString(c.getColumnIndex(TRADE_STATU)));
            items.add(item);

            if (items.size() == 50) { // 只需要50条
                break;
            }
        }
        c.close();

        return items;
    }

    *//**
     * 通过Event查询数据库中相应的前50条记录
     *//*
    public static JSONArray findRecordDataDetailArrayByEvent(
            Context context, String event) {
        SQLiteDatabase db = AppSqlHelper.getInstance(context)
                .getWritableDatabase();
        JSONArray array = new JSONArray();
        Cursor c = db.rawQuery(
                "select * from record_data_table where event = '"
                        + event + "'", null);
        while (c.moveToNext()) {
            JSONObject jsonObject = new JSONObject();
            JSONUtil.setString(jsonObject, "id", c.getInt(c.getColumnIndex(ID)) + "");
            JSONUtil.setString(jsonObject, "event", c.getString(c.getColumnIndex(EVENT)));
            JSONUtil.setString(jsonObject, "actionId", c.getString(c.getColumnIndex(ACTION_ID)));
            JSONUtil.setString(jsonObject, "pageName", c.getString(c.getColumnIndex(PAGE_NAME)));
            JSONUtil.setString(jsonObject, "timeIn", c.getString(c.getColumnIndex(TIME_IN)));
            JSONUtil.setString(jsonObject, "timeOut", c.getString(c.getColumnIndex(TIME_OUT)));
            JSONUtil.setString(jsonObject, "userId", c.getString(c.getColumnIndex(USER_ID)));
            JSONUtil.setString(jsonObject, "deviceModel", c.getString(c.getColumnIndex(DEVICE_MODEL)));
            JSONUtil.setString(jsonObject, "deviceType", c.getString(c.getColumnIndex(DEVICE_TYPE)));
            JSONUtil.setString(jsonObject, "deviceOsType", c.getString(c.getColumnIndex(DEVICE_OS_TYPE)));
            JSONUtil.setString(jsonObject, "versionCode", c.getString(c.getColumnIndex(VERSION_CODE)));
            JSONUtil.setString(jsonObject, "tradeStatu", c.getString(c.getColumnIndex(TRADE_STATU)));
            array.put(jsonObject);

            int totalCount = 99;
            if (Constant.isDebug)
                totalCount = 10;
            if (array.length() == totalCount) { // 只需要100条
                break;
            }
        }
        c.close();

        return array;
    }

    *//**
     * 删除所有文件信息
     *//*
    public static void deleteAllFileInfoDetail(Context context) {
        SQLiteDatabase db = AppSqlHelper.getInstance(context)
                .getWritableDatabase();
        db.beginTransaction();
        try {
            db.execSQL("delete from record_data_table");
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }*/

}
