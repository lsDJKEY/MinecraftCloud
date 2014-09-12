package io.ibj.onevone.persistance.cassandra;

import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joe on 9/8/2014.
 */
public class CInst {

    public CInst(Session session){
        this.session = session;
        statementMap = new HashMap<>();
    }

    @Getter

    private Session session;
    private Map<String, PreparedStatement> statementMap;

    public ResultSet execute(String queryStatement, Object... bind){
        PreparedStatement cached = statementMap.get(queryStatement);
        if(cached == null) {
            cached = session.prepare(queryStatement);
            statementMap.put(queryStatement, cached);
        }
        return session.execute(cached.bind(bind));
    }

    public ResultSet execute(Statement statement, Object... bind){
        String queryStatement = statement.getQueryString();
        PreparedStatement cached = statementMap.get(queryStatement);
        if(cached == null) {
            cached = session.prepare(statement);
            statementMap.put(queryStatement, cached);
        }
        return session.execute(cached.bind(bind));
    }

    public void setSession(Session session){
        this.session = session;
        statementMap.clear();
    }

}
