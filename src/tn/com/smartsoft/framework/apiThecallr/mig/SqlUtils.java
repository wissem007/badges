package tn.com.smartsoft.framework.apiThecallr.mig;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlUtils {
	
	public static void selectData(String request, Object[] params, IExecutorRow ex, GenerateurDB connection) {
		try {
			// System.out.println(request);
			PreparedStatement ps = connection.getConnection().prepareStatement(request);
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i + 1, params[i]);
			}
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ex.run(rs);
			}
			closeRs(rs);
			closePr(ps);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateData(String insetRequest, Object[] valus, GenerateurDB connection) {
		StringBuffer sb = new StringBuffer();
		try {
			PreparedStatement inserst = connection.getConnection().prepareStatement(insetRequest);
			for (int i = 0; i < valus.length; i++) {
				if (valus[i] instanceof Long) {
					inserst.setLong(i + 1, (Long) valus[i]);
				} else if (valus[i] instanceof Date) {
					inserst.setDate(i + 1, (Date) valus[i]);
				} else if (valus[i] instanceof String) {
					inserst.setString(i + 1, (String) valus[i]);
				} else if (valus[i] instanceof Double) {
					inserst.setDouble(i + 1, (Double) valus[i]);
				} else
					inserst.setObject(i + 1, valus[i]);
				sb.append(valus[i] + ",");
			}
			System.out.println("Update " + sb);
			inserst.executeUpdate();
			try {
				inserst.close();
			} catch (Exception e) {
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static int selectData(String request, Object[] params, GenerateurDB connection) {
		return selectData(request, params, connection, -1);
	}
	
	public static int selectData(String request, Object[] params, GenerateurDB connection, int defaultValue) {
		try {
			System.out.println(request);
			PreparedStatement ps = connection.getConnection().prepareStatement(request);
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i + 1, params[i]);
			}
			ResultSet rs = ps.executeQuery();
			int value = defaultValue;
			if (rs.next())
				value = rs.getInt(1);
			closeRs(rs);
			closePr(ps);
			return value;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String selectStringData(String request, Object[] params, GenerateurDB connection) {
		try {
			System.out.println(request);
			PreparedStatement ps = connection.getConnection().prepareStatement(request);
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i + 1, params[i]);
			}
			ResultSet rs = ps.executeQuery();
			String value = null;
			if (rs.next())
				value = rs.getString(1);
			closeRs(rs);
			closePr(ps);
			return value;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static boolean selectExistData(GenerateurDB connection, String request) {
		return selectExistData(request, new Object[] {}, connection);
	}
	
	public static boolean selectExistData(String request, Object[] params, GenerateurDB connection) {
		try {
			PreparedStatement ps = connection.getConnection().prepareStatement(request);
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i + 1, params[i]);
			}
			ResultSet rs = ps.executeQuery();
			boolean is = rs.next();
			try {
				rs.close();
			} catch (Exception e) {
			}
			try {
				ps.close();
			} catch (Exception e) {
			}
			return is;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static void closeRs(ResultSet rs) {
		try {
			rs.close();
		} catch (Exception e) {
		}
	}
	
	public static void closePr(PreparedStatement inserst) {
		try {
			inserst.close();
		} catch (Exception e) {
		}
	}
}
