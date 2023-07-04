package cash.model;
import java.sql.*;

import javax.servlet.http.HttpSession;

import cash.vo.Member;

public class MemberDao {
	// 회원 상세정보
	public Member selectMemberOne(String memberId) {
		Member returnMember = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql="SELECT member_id, '****' AS member_pw, createdate, updatedate FROM member WHERE member_id=?";
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/cash","root","java1234");
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			rs = stmt.executeQuery();
			if(rs.next()) {
				returnMember = new Member();
				returnMember.setMemberId(rs.getString("member_id"));
				returnMember.setMemberPw(rs.getString("member_pw"));
				returnMember.setCreatedate(rs.getString("createdate"));
				returnMember.setUpdatedate(rs.getString("updatedate"));
			}

		} catch(Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();	
			} catch(Exception e2){
				e2.printStackTrace();
			}
		}
		
		return returnMember;
	}
	// 회원가입 메서드
	public int insertMember(Member member) {
		int row = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		String sql = "INSERT INTO member (member_id, member_pw, createdate, updatedate) VALUES(?,PASSWORD(?),NOW(),NOW())";
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/cash","root","java1234");
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, member.getMemberId());
			stmt.setString(2, member.getMemberPw());
			row = stmt.executeUpdate();

		} catch(Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();	
			} catch(Exception e2){
				e2.printStackTrace();
			}
		}
		
		return row;
	}
	
	// 로그인 메서드
	public Member selectMemberById(Member paramMember) {
		Member returnMember = null;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT member_id memberId FROM member WHERE member_id=? AND member_pw = PASSWORD(?)";
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/cash","root","java1234");
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, paramMember.getMemberId());
			stmt.setString(2, paramMember.getMemberPw());
			rs = stmt.executeQuery();
			if(rs.next()) {
				returnMember = new Member();
				returnMember.setMemberId(rs.getString("memberId"));
			}
		} catch(Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();	
			} catch(Exception e2){
				e2.printStackTrace();
			}
		}
		
		return returnMember;
	}
	// 회원정보 수정 메서드
	public int modifyMember(String memberId, String memberPw) {
	    int row = 0;
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    String sql = "UPDATE member SET member_pw = PASSWORD(?), updatedate = NOW() WHERE member_id = ?";
	    try {
	        Class.forName("org.mariadb.jdbc.Driver");
	        conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/cash", "root", "java1234");
	        stmt = conn.prepareStatement(sql);
	        stmt.setString(1, memberPw);
	        stmt.setString(2, memberId);
	        row = stmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            stmt.close();
	            conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    return row;
	}
	// 회원탈퇴 메서드
		public int removeMember(String memberId, String memberPw) {
			int row = 0;
			Connection conn = null;
			PreparedStatement stmt = null;
			String sql = "DELETE FROM member WHERE member_id = ? AND member_pw = PASSWORD(?)";
			try {
				Class.forName("org.mariadb.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/cash","root","java1234");
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, memberId);
				stmt.setString(2, memberPw);
				row = stmt.executeUpdate();
			} catch(Exception e1) {
				e1.printStackTrace();
			} finally {
				try {
					stmt.close();
					conn.close();	
				} catch(Exception e2){
					e2.printStackTrace();
				}
			}
			
			return row;
		}
}
