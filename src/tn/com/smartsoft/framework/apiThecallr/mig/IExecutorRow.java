package tn.com.smartsoft.framework.apiThecallr.mig;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IExecutorRow{

	void run(ResultSet rs) throws SQLException;
}
