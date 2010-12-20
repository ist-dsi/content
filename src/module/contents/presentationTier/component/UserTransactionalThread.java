package module.contents.presentationTier.component;

import jvstm.TransactionalCommand;
import myorg.applicationTier.Authenticate.UserView;
import pt.ist.fenixframework.pstm.Transaction;

public abstract class UserTransactionalThread extends Thread implements TransactionalCommand {

    private final UserView user = pt.ist.fenixWebFramework.security.UserView.getUser();

    @Override
    public void run() {
	try {
	    pt.ist.fenixWebFramework.security.UserView.setUser(user);
	    Transaction.withTransaction(true, this);
	} finally {
	    pt.ist.fenixWebFramework.security.UserView.setUser(null);
	}
    }

}
