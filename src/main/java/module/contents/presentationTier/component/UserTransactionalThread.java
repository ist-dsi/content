/*
 * @(#)UserTransactionalThread.java
 *
 * Copyright 2010 Instituto Superior Tecnico
 * Founding Authors: Luis Cruz, Paulo Abrantes
 * 
 *      https://fenix-ashes.ist.utl.pt/
 * 
 *   This file is part of the Content Module.
 *
 *   The Content Module is free software: you can
 *   redistribute it and/or modify it under the terms of the GNU Lesser General
 *   Public License as published by the Free Software Foundation, either version 
 *   3 of the License, or (at your option) any later version.
 *
 *   The Content Module is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with the Content Module. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package module.contents.presentationTier.component;

import jvstm.TransactionalCommand;
import pt.ist.bennu.core.applicationTier.Authenticate.UserView;
import pt.ist.fenixframework.pstm.Transaction;

/**
 * 
 * @author Luis Cruz
 * 
 */
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
