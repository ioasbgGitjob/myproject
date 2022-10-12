//package java反射;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.*;
//
//public class GeneralDAO {
////---------------------------------查询部分--------------------------------------------------------------------------
//
//    /**
//     * 查询所有记录
//     *
//     * @param obj表对应的JavaBean对象
//     * @throws Exception
//     * @return所有记录构成的list
//     */
//
//    public List findAll(Object obj) throws Exception {
//        List list = new ArrayList();
//
//        ResultSet rs = null;
//
//        try {
//// 获取数据库连接
//
//            Statement stmt = DBConnectionManager.getConnection()
//
//                    .createStatement();
//
//// 构建sql语句
//
//            String tableName = obj.getClass().getName();
//
//            tableName = tableName.substring(tableName.lastIndexOf(".") + 1);
//
//            String sql = "select * from " + tableName;
//
//// 执行sql语句
//
//            rs = stmt.executeQuery(sql);
//
//            while (rs.next()) {
//                list.add(createObj(rs, obj));
//
//            }
//
//        } catch (Exception e) {
//// TODO Auto-generated catch block
//
//            e.printStackTrace();
//
//            throw e;
//
//        } finally {
//// 关闭连接
//
//            if (rs != null) {
//                rs.close();
//
//            }
//
//            DBConnectionManager.closeConnection();
//
//        }
//
//        return list;
//
//    }
//
//    /**
//     * 对指定的表执行指定的sql查询语句
//     *
//     * @param obj   以该表对应的JavaBean对象决定表
//     * @param query 要对该表执行的sql语句
//     * @return
//     * @throws Exception
//     */
//
//    public List findByNameQuery(Object obj, String query) throws Exception {
//// TODO Auto-generated method stub
//
//        List list = null;
//
//        try {
//            Statement sql_statement = DBConnectionManager.getConnection()
//
//                    .createStatement();
//
////执行传递的sql语句
//
//            ResultSet rs = sql_statement.executeQuery(query);
//
//            list = new ArrayList();
//
//            if (rs != null) {
//                while (rs.next()) {
//                    list.add(createObj(rs, obj));
//
//                }
//
//            }
//
//            return list;
//
//        } catch (Exception e) {
//// TODO Auto-generated catch block
//
//            e.printStackTrace();
//
//            throw e;
//
//        } finally {
//            DBConnectionManager.closeConnection();
//
//        }
//
//    }
//
//    /**
//     * 按条件对指定表进行查询
//     *
//     * @param obj       以该表对应的JavaBean对象决定表
//     * @param condition 查询的条件，key：条件value：值
//     * @param isFuzzzy  是否支持模糊查询
//     * @return
//     * @throws Exception
//     */
//
//    public List findByNameQuery(Object obj, Map condition, boolean isFuzzy) throws Exception {
//        List list = new ArrayList();
//
//        try {
//            String tablename = getTableName(obj);
//
//            StringBuilder sb = new StringBuilder("select * from " + tablename);
//
//            PreparedStatement state = null;
//
//            if (isFuzzy) {
//                addFuzzyConditionString(sb, condition);
//
//            } else {
//                addConditionString(sb, condition);
//
//            }
//
//            state = DBConnectionManager.getConnection().prepareStatement(sb.toString());
//
//            state = setSqlValue(state, condition, isFuzzy);
//
//            ResultSet rs = state.executeQuery();
//
//            if (rs != null) {
//                while (rs.next()) {
//                    list.add(createObj(rs, obj));
//
//                }
//
//            }
//
//            return list;
//
//        } catch (Exception e) {
//// TODO Auto-generated catch block
//
//            e.printStackTrace();
//
//            throw e;
//
//        } finally {
//            DBConnectionManager.closeConnection();
//
//        }
//
//    }
//
////-------------------------------------创建部分--------------------------------------------------------------------------
//
//    /**
//     * 在数据库里新建一条记录
//     *
//     * @param obj 描述记录的对象,必须是数据库中某张表对应的JavaBean对象
//     * @return 新建成功返回真，否则返回假
//     * @throws Exception
//     */
//
//    public boolean create(Object obj) throws Exception {
//// 获取数据库连接
//
//        try {
//// 构建sql语句
//
//            Map map = getObjectFieldValue(obj);
//
//            String tableName = obj.getClass().getName();
//
//            tableName = tableName.substring(tableName.lastIndexOf(".") + 1);
//
//            StringBuilder sb = new StringBuilder("insert into " + tableName);
//
//            addCreateString(sb, map);
//
//            PreparedStatement stmt = DBConnectionManager.getConnection()
//
//                    .prepareStatement(sb.toString());
//
//            setSqlValue(stmt, map, false);
//
//            int count = stmt.executeUpdate();
//
//            if (count > 0) {
//                return true;
//
//            } else {
//                return false;
//
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//
//            throw e;
//
//        } finally {
//            DBConnectionManager.closeConnection();
//
//        }
//
//    }
//
////-------------------------------------删除部分----------------------------------------------------------------------------
//
//    /**
//     * 删除表中所有记录
//     *
//     * @param obj JavaBean对象，决定要删除的是哪张表
//     * @return 删除成功返回真，否则返回假
//     */
//
//    public boolean deleteAll(Object obj) throws Exception {
//        String delete_sql = "delete from " + getTableName(obj);
//
//        PreparedStatement pstmt;
//
//        try {
//            pstmt = DBConnectionManager.getConnection().prepareStatement(delete_sql);
//
//            int count = pstmt.executeUpdate();
//
//            if (count > 0) {
//                return true;
//
//            } else {
//                return false;
//
//            }
//
//        } catch (Exception e) {
//// TODO Auto-generated catch block
//
//            e.printStackTrace();
//
//            throw e;
//
//        } finally {
//            DBConnectionManager.closeConnection();
//
//        }
//
//    }
//
//    /**
//     * 在指定表中删除给定ID的记录
//     *
//     * @param obj       表示要删除的表
//     * @param condition 删除的条件 key:字段(一般为字符串) value:值
//     * @return
//     */
//
//    public boolean delete(Object obj, Map condition) throws Exception {
////构建sql语句
//
//        StringBuilder sb = new StringBuilder("delete from " + getTableName(obj));
//
//        addConditionString(sb, condition);
//
//        try {
//            PreparedStatement pstmt = DBConnectionManager.getConnection().prepareStatement(sb.toString());
//
//            setSqlValue(pstmt, condition, false);
//
//            int count = pstmt.executeUpdate();
//
//            if (count > 0) {
//                return true;
//
//            } else {
//                return false;
//
//            }
//
//        } catch (Exception e) {
//// TODO Auto-generated catch block
//
//            e.printStackTrace();
//
//            throw e;
//
//        } finally {
//            DBConnectionManager.closeConnection();
//
//        }
//
//    }
//
////-------------------------------------更新部分----------------------------------------------------------------------------
//
//    /**
//     * 更新指定表的记录
//     *
//     * @param obj       用来确定表的Java对象
//     * @param map       要设置的字段值，key：字段(字符串),value：值
//     * @param condition 筛选条件
//     * @return
//     */
//
//    public boolean update(Object obj, Map map, Mapcondition) throws Exception {
//        StringBuilder sb = new StringBuilder("update " + getTableName(obj));
//
//        addSetString(sb, map);
//
//        addConditionString(sb, condition);
//
//        try {
//            PreparedStatement pstmt = DBConnectionManager.getConnection().prepareStatement(sb.toString());
//
//            List values = new ArrayList();
//
//            getValuesToList(values, map);
//
//            getValuesToList(values, condition);
//
//            setSqlValue(pstmt, values);
//
//            int count = pstmt.executeUpdate();
//
//            if (count > 0) {
//                return true;
//
//            } else {
//                return false;
//
//            }
//
//        } catch (Exception e) {
//// TODO Auto-generated catch block
//
//            e.printStackTrace();
//
//            throw e;
//
//        } finally {
//            DBConnectionManager.closeConnection();
//
//        }
//
//    }
//
//    private List getValuesToList(List values, Map map) {
//        for (Iterator it = map.keySet().iterator(); it.hasNext(); ) {
//            Object value = map.get(it.next());
//
//            values.add(value);
//
//        }
//
//        return values;
//
//    }
//
//    /**
//     * 获取JavaBean对象的字段值放到容器中
//     *
//     * @param obj JavaBean对象
//     * @return
//     * @throws IllegalAccessException
//     * @throws InvocationTargetException
//     */
//
//    private Map getObjectFieldValue(Object obj)
//
//            throws IllegalAccessException, InvocationTargetException {
//        Map map = new HashMap();
//
//        Field fields[] = obj.getClass().getFields();
//
//        Method methods[] = obj.getClass().getMethods();
//
//// 给字段设置值
//
//        for (int i = 0; i < fields.length; i++) {
//// 用字段的名字构建set方法的名字
//
//            String fieldName = fields[i].getName();
//
//            String methodName = "get" + fieldName.substring(0, 1).toUpperCase()
//
//                    + fieldName.substring(1);
//
//// 查找对应的方法
//
//            Method method = null;
//
//            for (int j = 0; j < methods.length; j++) {
//                if (methods[j].getName().equals(methodName)) {
//                    method = methods[j];
//
//                    break;
//
//                }
//
//            }
//
//// 如果存在这个方法
//
//            if (method != null) {
//// 开始执行方法，获取当前的JavaBean对象字段值
//
//                Object value = method.invoke(obj, new Object[]{});
//
//                map.put(fieldName, value);
//
//            }
//
//        }
//
//        return map;
//
//    }
//
//    private void addSetString(StringBuilder sb, Map map) {
//// TODO Auto-generated method stub
//
//        if (!map.isEmpty()) {
//            sb.append(" set ");
//
//            for (Iterator it = map.keySet().iterator(); it.hasNext(); ) {
//                String key = (String) it.next();
//
//                if (null == key) {
//                    continue;
//
//                }
//
//                sb.append(key + "=?,");
//
//            }
//
//            sb.deleteCharAt(sb.lastIndexOf(","));
//
//            System.out.println(sb.toString());
//
//        }
//
//    }
//
//    /**
//     * 在现有sql创建语句后面追加字段名和占位符
//     *
//     * @param sb
//     * @param map
//     */
//
//    private void addCreateString(StringBuilder sb, Map map) {
//// TODO Auto-generated method stub
//
//        sb.append("(");
//
//        int count = 0;
//
//        for (Iterator it = map.keySet().iterator(); it.hasNext(); ) {
//            String key = (String) it.next();
//
//            if (null == key) {
//                continue;
//
//            }
//
//            sb.append(key + ",");
//
//            count++;
//
//        }
//
//        sb.deleteCharAt(sb.lastIndexOf(","));
//
//        sb.append(") values(");
//
//        for (int i = 0; i < count; i++) {
//            sb.append("?,");
//
//        }
//
//        sb.deleteCharAt(sb.lastIndexOf(","));
//
//        sb.append(")");
//
//        System.out.println(sb.toString());
//
//    }
//
//    /**
//     * 在现有sql查询语句后面追加判断条件
//     *
//     * @param sb        where之前的sql
//     * @param condition 条件(key:字段名；value:值)
//     * @return使用占位符的sql语句
//     */
//
//    private String addConditionString(StringBuilder sb, Map condition) {
//        if (!condition.isEmpty()) {
//            sb.append(" where ");
//
//            int i = 0;
//
//            for (Iterator it = condition.keySet().iterator(); it.hasNext(); ) {
//                String key = (String) it.next();
//
//                if (null == key) {
//                    continue;
//
//                }
//
//                if (i != 0) {
//                    sb.append(" and ");
//
//                }
//
//                if (key.indexOf("") != -1
//
//                        || key.indexOf("=") != -1) {
//                    sb.append(key + "?");
//
//                } else {
//                    sb.append(key + "=" + "?");
//
//                }
//
//                i++;
//
//            }
//
//        }
//
//        System.out.println(sb.toString());
//
//        return sb.toString();
//
//    }
//
//    private String addFuzzyConditionString(StringBuilder sb, Mapcondition) {
//        if (!condition.isEmpty()) {
//            sb.append(" where ");
//
//            int i = 0;
//
//            for (Iterator it = condition.keySet().iterator(); it.hasNext(); ) {
//                String key = (String) it.next();
//
//                if (null == key) {
//                    continue;
//
//                }
//
//                if (i != 0) {
//                    sb.append(" or ");
//
//                }
//
//                sb.append(key + " like " + "?");
//
//                i++;
//
//            }
//
//        }
//
//        System.out.println(sb.toString());
//
//        return sb.toString();
//
//    }
//
//    /**
//     * 设置现有sql语句的占位符的值
//     *
//     * @param state
//     * @param map
//     * @return
//     * @throws SQLException
//     */
//
//    private PreparedStatement setSqlValue(PreparedStatement state, Map map, boolean isFuzzy)
//
//            throws SQLException {
//        if (!map.isEmpty()) {
//            int i = 1;
//
//            for (Iterator it = map.keySet().iterator(); it.hasNext(); ) {
//                Object value = map.get(it.next());
//
//                if (isFuzzy) {
//                    state.setObject(i++, "%" + value + "%");
//
//                } else {
//                    state.setObject(i++, value);
//
//                }
//
//            }
//
//        }
//
//        return state;
//
//    }
//
//    private PreparedStatement setSqlValue(PreparedStatement state, List values)
//
//            throws SQLException {
//        if (!values.isEmpty()) {
//            int i = 1;
//
//            for (Iterator it = values.iterator(); it.hasNext(); ) {
//                Object value = it.next();
//
//                state.setObject(i++, value);
//
//            }
//
//        }
//
//        return state;
//
//    }
//
//    private String getTableName(Object obj) {
//        String tablename = obj.getClass().getName();
//
//        tablename = tablename.substring(tablename.lastIndexOf(".") + 1);
//
//        return tablename;
//
//    }
//
//// 利用反射构建对象
//
//    private Object createObj(ResultSet rs, Object obj)
//
//            throws Exception {
//// TODO Auto-generated method stub
//
//        Object object = obj.getClass().newInstance();
//
//// 获取字段
//
//        Field fields[] = obj.getClass().getFields();
//
//// 获取方法
//
//        Method methods[] = obj.getClass().getMethods();
//
//// 给字段设置值
//
//        for (int i = 0; i < fields.length; i++) {
//// 用字段的名字构建set方法的名字
//
//            String fieldName = fields[i].getName();
//
//            String methodName = "set" + fieldName.substring(0, 1).toUpperCase()
//
//                    + fieldName.substring(1);
//
//// 查找对应的方法
//
//            Method method = null;
//
//            for (int j = 0; j < methods.length; j++) {
//                if (methods[j].getName().equals(methodName)) {
//                    method = methods[j];
//
//                    break;
//
//                }
//
//            }
//
//// 如果存在这个方法
//
//            if (method != null) {
//// 开始执行方法，为当前的JavaBean对象字段设置值
//
//                Object value = rs.getObject(fieldName);
//
//                method.invoke(object, new Object[]{value});
//
//            }
//
//        }
//
//        return object;
//
//    }
//
//}