package org.example.database.joins;

import org.example.database.JoinStrategy;

public class JoinFactory {
    public static JoinStrategy getJoinStrategy(String joinType) {
        switch (joinType.toLowerCase()) {
            case "inner":
                return (JoinStrategy) new InnerJoin();
            case "left":
                return (JoinStrategy) new LeftOuterJoin();
            case "right":
                return (JoinStrategy) new RightOuterJoin();
            case "full":
                return (JoinStrategy) new FullOuterJoin();
            case "cross":
                return (JoinStrategy) new CrossJoin();
            case "self":
                return new SelfJoin();
            case "natural":
                return new NaturalJoin();
            case "equi":
                return new EquiJoin();
            case "non-equi":
                return new NonEquiJoin();
            default:
                return null;
        }
    }
}
