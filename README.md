# amazing-gift-sql
Base on JDBC and JDK1.8, make data access object more simple: Focus on sql string, requesting parameters and results when database is needed.

just an example:

public class UserDaoImpl implements UserDao {

	// @Autowired
	SqlSession sqlSession;

	@Override
	public List<User> select(int offset, int count) {
		List<User> users = new ArrayList<User>();
		sqlSession.query("select * from t_user limit ?, ?", (PreparedStatement preparedStatement) -> {
			preparedStatement.setInt(1, offset);
			preparedStatement.setInt(2, count);
		}, (ResultSet resultSet) -> {
			while (resultSet.next()) {
				User user = new User();
				user.setUserId(resultSet.getString("user_id"));
				user.setUserName(resultSet.getString("user_name"));
				user.setUserDescription(resultSet.getString("user_description"));
				users.add(user);
			}
			return users.size();
		});
		return users;
	}

	@Override
	public int create(User user) {
		return sqlSession.update("insert into t_user (user_id, user_name, user_description) values (?, ?, ?)",
				(PreparedStatement preparedStatement) -> {
					preparedStatement.setString(1, user.getUserId());
					preparedStatement.setString(2, user.getUserName());
					preparedStatement.setString(3, user.getUserDescription());
				});
	}

	@Override
	public int create(List<User> users) {
		return sqlSession.updateBatch("insert into t_user (user_id, user_name, user_description) values (?, ?, ?)",
				(PreparedStatement preparedStatement) -> {
					for (User user : users) {
						preparedStatement.setString(1, user.getUserId());
						preparedStatement.setString(2, user.getUserId());
						preparedStatement.setString(3, user.getUserId());
						preparedStatement.addBatch();
					}
				});
	}

}
