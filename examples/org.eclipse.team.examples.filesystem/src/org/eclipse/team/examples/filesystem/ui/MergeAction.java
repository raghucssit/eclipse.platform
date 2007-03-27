/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.team.examples.filesystem.ui;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.jface.action.IAction;
import org.eclipse.team.ui.synchronize.ModelMergeOperation;

/**
 * This merge action is contributed as a a popupmenu objectContribution in
 * the plugin.xml. You can change the value return from {@link #isUseSyncFramework()}
 * to try out different dialogs.
 * 
 * @since 3.2
 */
public class MergeAction extends FileSystemAction {

	protected void execute(IAction action) throws InvocationTargetException,
			InterruptedException {
		try {
			ModelMergeOperation operation;
			if (isUseSyncFramework()) {
				operation = new SyncDialogModelMergeOperation(getTargetPart(),
						FileSystemOperation.createScopeManager("Merging", getSelectedMappings()));
			} else {
				operation = new NonSyncModelMergeOperation(getTargetPart(),
						FileSystemOperation.createScopeManager("Merging", getSelectedMappings()));
			}
			operation.run();
		} catch (InvocationTargetException e) {
			handle(e, null, "Errors occurred while merging");
		} catch (InterruptedException e) {
			// Ignore
		}
	}

	private boolean isUseSyncFramework() {
		return true;
	}
}
