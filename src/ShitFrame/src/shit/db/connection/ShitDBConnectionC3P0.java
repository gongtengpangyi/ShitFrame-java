package shit.db.connection;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import shit.db.cfg.ShitDBC3P0DataSource;
import shit.db.cfg.ShitDBDataSource;
import shit.db.exception.ShitDBConfigureException;
import shit.db.exception.ShitDBConnectException;

/**
 * 基于C3P0的数据库连接获取
 * 需要添加jdbc和C3P0的jar包
 * @author GongTengPangYi
 *
 */
public class ShitDBConnectionC3P0 implements ShitDBConnection {
	
    private ComboPooledDataSource ds = new ComboPooledDataSource();  
      
    public DataSource getDataSource() {  
        return ds;  
    }
    
    public void setDataSource(ShitDBDataSource shitDataSource) throws ShitDBConfigureException {
    	ds = ((ShitDBC3P0DataSource) shitDataSource).getComboPooledDataSource();
    }
   
	@Override
	public Connection getConnection() throws ShitDBConnectException {
        try {
			return (Connection) ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ShitDBConnectException(e);
		}  
	}

	@Override
	public void close(Connection connection) throws ShitDBConnectException {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ShitDBConnectException(e);
		}

	}

}
