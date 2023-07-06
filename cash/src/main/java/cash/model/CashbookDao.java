package cash.model;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cash.vo.*;

public class CashbookDao {
	// 가계부 입력
	// 반환값 : cashbook_no 키값
	public int insertCashbook(Cashbook cashbook) {
		int cashbookNo = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null; //입력 후 생성된 키값 반환
		String sql = "INSERT INTO cashbook"
				+ " (member_id, category, cashbook_date, price, memo, createdate, updatedate)"
				+ " VALUES(?, ?, ?, ?, ?, NOW(), NOW())";
		try {
			String driver = "org.mariadb.jdbc.Driver";
			String dbUrl = "jdbc:mariadb://127.0.0.1:3306/cash";
			String dbUser = "root";
			String dbPw = "java1234";
			Class.forName(driver);
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, cashbook.getMemberId());
			stmt.setString(2, cashbook.getCategory());
			stmt.setString(3, cashbook.getCashbookDate());
			stmt.setInt(4, cashbook.getPrice());
			stmt.setString(5, cashbook.getMemo());
			int row = stmt.executeUpdate();
			if (row > 0) {
			    rs = stmt.getGeneratedKeys();
			    if (rs.next()) {
			        cashbookNo = rs.getInt(1);
			    }
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		return cashbookNo;
	}
	// 월별 가계부 목록 조회
	public List<Cashbook> selectCashbookListByMonth(String memberId, int targetYear, int targetMonth){
	List<Cashbook> list = new ArrayList<Cashbook>();
	Connection conn = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;
	String sql = "SELECT cashbook_no cashbookNo, category, price, cashbook_date cashbookDate"
				+ " FROM cashbook"
				+ " WHERE member_id=? AND YEAR(cashbook_date)=? AND MONTH(cashbook_date)=?"
				+ " ORDER BY cashbook_date ASC";
		try {
			String driver = "org.mariadb.jdbc.Driver";
			String dbUrl = "jdbc:mariadb://127.0.0.1:3306/cash";
			String dbUser = "root";
			String dbPw = "java1234";
			Class.forName(driver);
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			stmt.setInt(2, targetYear);
			stmt.setInt(3, targetMonth);
			System.out.println(stmt);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Cashbook c = new Cashbook();
				c.setCashbookNo(rs.getInt("cashbookNo"));
				c.setCategory(rs.getString("category"));
				c.setPrice(rs.getInt("price"));
				c.setCashbookDate(rs.getString("cashbookDate"));
				list.add(c);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		return list;
	}
	// 날짜별 가계부 조회
	public List<Cashbook> selectCashbookListByDate(String memberId, int targetYear, int targetMonth, int targetDate){
		List<Cashbook> list = new ArrayList<Cashbook>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT cashbook_no cashbookNo, category, price, memo, cashbook_date cashbookDate, createdate, updatedate"
					+ " FROM cashbook"
					+ " WHERE member_id=? AND YEAR(cashbook_date)=? AND MONTH(cashbook_date)=? AND DAY(cashbook_date)=?"
					+ " ORDER BY cashbook_date ASC";
		try {
			String driver = "org.mariadb.jdbc.Driver";
			String dbUrl = "jdbc:mariadb://127.0.0.1:3306/cash";
			String dbUser = "root";
			String dbPw = "java1234";
			Class.forName(driver);
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			stmt.setInt(2, targetYear);
			stmt.setInt(3, targetMonth);
			stmt.setInt(4, targetDate);
			rs = stmt.executeQuery();
			System.out.println(stmt+"Date stmt");
			while(rs.next()) {
				Cashbook c = new Cashbook();
				c.setCashbookNo(rs.getInt("cashbookNo"));
				c.setCategory(rs.getString("category"));
				c.setPrice(rs.getInt("price"));
				c.setMemo(rs.getString("memo"));
				c.setCashbookDate(rs.getString("cashbookDate"));
				c.setCreatedate(rs.getString("createdate"));
				c.setUpdatedate(rs.getString("updatedate"));
				list.add(c);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		return list;
	}
	// 하나의 가계부 글 조회(수정시 사용예정)
	public Cashbook selectCashbookListByDateOne(String memberId, int targetYear, int targetMonth, int targetDate){
		Cashbook cashbook = new Cashbook();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT cashbook_no cashbookNo, category, price, memo, cashbook_date cashbookDate, createdate, updatedate"
					+ " FROM cashbook"
					+ " WHERE member_id=? AND YEAR(cashbook_date)=? AND MONTH(cashbook_date)=? AND DAY(cashbook_date)=?"
					+ " ORDER BY cashbook_date ASC";
		try {
			String driver = "org.mariadb.jdbc.Driver";
			String dbUrl = "jdbc:mariadb://127.0.0.1:3306/cash";
			String dbUser = "root";
			String dbPw = "java1234";
			Class.forName(driver);
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			stmt.setInt(2, targetYear);
			stmt.setInt(3, targetMonth);
			stmt.setInt(4, targetDate);
			rs = stmt.executeQuery();
			System.out.println(stmt+"Date stmt");
			if(rs.next()) {
				cashbook = new Cashbook();
				cashbook.setCashbookNo(rs.getInt("cashbookNo"));
				cashbook.setCategory(rs.getString("category"));
				cashbook.setPrice(rs.getInt("price"));
				cashbook.setMemo(rs.getString("memo"));
				cashbook.setCashbookDate(rs.getString("cashbookDate"));
				cashbook.setCreatedate(rs.getString("createdate"));
				cashbook.setUpdatedate(rs.getString("updatedate"));
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		return cashbook;
	}
	// 태그 별 글 조회 (태그를 포함한 글을 모두 찾아줌)
	public List<Cashbook> selectCashbookListByTag(String memberId, String word, int beginRow, int rowPerPage){
		List<Cashbook> list = new ArrayList<Cashbook>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT c.* FROM cashbook c INNER JOIN hashtag h ON c.cashbook_no = h.cashbook_no WHERE c.member_id = ? AND h.word = ? ORDER BY c.cashbook_date DESC LIMIT ?, ?"; //join해야
			try {
				String driver = "org.mariadb.jdbc.Driver";
				String dbUrl = "jdbc:mariadb://127.0.0.1:3306/cash";
				String dbUser = "root";
				String dbPw = "java1234";
				Class.forName(driver);
				conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, memberId);
				stmt.setString(2, word);
				stmt.setInt(3, beginRow);
				stmt.setInt(4, rowPerPage);
				System.out.println(stmt);
				rs = stmt.executeQuery();
				while(rs.next()) {
					Cashbook c = new Cashbook();
					c.setCashbookNo(rs.getInt("c.cashbook_no"));
					c.setCategory(rs.getString("c.category"));
					c.setPrice(rs.getInt("c.price"));
					c.setMemo(rs.getString("c.memo"));
					c.setCashbookDate(rs.getString("c.cashbook_date"));
					c.setCreatedate(rs.getString("c.createdate"));
					c.setUpdatedate(rs.getString("c.updatedate"));
					list.add(c);
				}
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				try {
					rs.close();
					stmt.close();
					conn.close();
				} catch(Exception e2) {
					e2.printStackTrace();
				}
			}
			return list;
	}
	// hashtag 별 조회시 페이징하기 위한 totalRow 쿼리
	public int cashbookTotalRow(String memberId, String word){
		int row = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT COUNT(*) AS count"
				+ " FROM cashbook c"
				+ " INNER JOIN hashtag h ON c.cashbook_no = h.cashbook_no"
				+ " WHERE c.member_id = ? AND h.word = ?";
			try {
				String driver = "org.mariadb.jdbc.Driver";
				String dbUrl = "jdbc:mariadb://127.0.0.1:3306/cash";
				String dbUser = "root";
				String dbPw = "java1234";
				Class.forName(driver);
				conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, memberId);
				stmt.setString(2, word);
				System.out.println(stmt+" <-- cashbookTotalRow");
				rs = stmt.executeQuery();
				if(rs.next()) {
					row = rs.getInt("count");
				}
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				try {
					rs.close();
					stmt.close();
					conn.close();
				} catch(Exception e2) {
					e2.printStackTrace();
				}
			}
		return row;
	}
	public int selectCashbookNo(int cashbookNo) {
		int hashtagCashbookNo = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT count(*) count FROM hashtag WHERE cashbook_no = ?";
		try {
			String driver = "org.mariadb.jdbc.Driver";
			String dbUrl = "jdbc:mariadb://127.0.0.1:3306/cash";
			String dbUser = "root";
			String dbPw = "java1234";
			Class.forName(driver);
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, cashbookNo);
			System.out.println(stmt+" <-- selectCashbookNo stmt");
			rs = stmt.executeQuery();
			if(rs.next()) {
				hashtagCashbookNo = rs.getInt("count");
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		return hashtagCashbookNo;	
	}
	// 가계부 삭제(hashtag의 word도 함께 삭제)
	public int removeCashbook(int cashbookNo){
		int row = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		String sql = "DELETE c, h FROM cashbook c JOIN hashtag h ON c.cashbook_no = h.cashbook_no WHERE c.cashbook_no = ?";
			try {
				String driver = "org.mariadb.jdbc.Driver";
				String dbUrl = "jdbc:mariadb://127.0.0.1:3306/cash";
				String dbUser = "root";
				String dbPw = "java1234";
				Class.forName(driver);
				conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, cashbookNo);
				System.out.println(stmt+" <-- removeCashbook");
				row = stmt.executeUpdate();
				System.out.println(row+" <-- removeCashbook row");
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				try {
					stmt.close();
					conn.close();
				} catch(Exception e2) {
					e2.printStackTrace();
				}
			}
		return row;
	}
	// cashbook에서만 가계부 삭제
		public int removeOnlyCashbook(int cashbookNo){
			int row = 0;
			Connection conn = null;
			PreparedStatement stmt = null;
			String sql = "DELETE FROM cashbook WHERE cashbook_no = ?";
				try {
					String driver = "org.mariadb.jdbc.Driver";
					String dbUrl = "jdbc:mariadb://127.0.0.1:3306/cash";
					String dbUser = "root";
					String dbPw = "java1234";
					Class.forName(driver);
					conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
					stmt = conn.prepareStatement(sql);
					stmt.setInt(1, cashbookNo);
					System.out.println(stmt+" <-- removeOnlyCashbook");
					row = stmt.executeUpdate();
					System.out.println(row+" <-- removeOnlyCashbook row");
				} catch(Exception e) {
					e.printStackTrace();
				} finally {
					try {
						stmt.close();
						conn.close();
					} catch(Exception e2) {
						e2.printStackTrace();
					}
				}
			return row;
		}
}

