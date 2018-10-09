/*
 * Copyright 2018 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jbpm.workbench.wi.client.editors.deployment.descriptor.sections.environmententries;

import org.jbpm.workbench.wi.client.editors.deployment.descriptor.items.ItemObjectModelFactory;
import org.jbpm.workbench.wi.dd.model.DeploymentDescriptorModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.workbench.common.screens.library.client.settings.SettingsSectionChange;
import org.kie.workbench.common.screens.library.client.settings.util.sections.MenuItem;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.uberfire.client.promise.Promises;
import org.uberfire.mocks.EventSourceMock;
import org.uberfire.promise.SyncPromises;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DeploymentsEnvironmentEntriesPresenterTest {

    @Mock
    private EventSourceMock<SettingsSectionChange<DeploymentDescriptorModel>> settingsSectionChangeEvent;

    @Mock
    private MenuItem<DeploymentDescriptorModel> menuItem;

    @Mock
    private DeploymentsEnvironmentEntriesView view;

    @Mock
    private DeploymentsEnvironmentEntriesPresenter.EnvironmentEntriesListPresenter environmentEntriesListPresenter;

    @Mock
    private ItemObjectModelFactory itemObjectModelFactory;

    private Promises promises = new SyncPromises();

    private DeploymentsEnvironmentEntriesPresenter presenter;

    @Before
    public void before() {
        this.presenter = spy(new DeploymentsEnvironmentEntriesPresenter(settingsSectionChangeEvent,
                                                                      menuItem,
                                                                      promises,
                                                                      view,
                                                                      environmentEntriesListPresenter,
                                                                      itemObjectModelFactory));
    }

    @Test
    public void testSetup() {
        final DeploymentDescriptorModel model = new DeploymentDescriptorModel();

        presenter.setup(model);

        assertNotNull(model.getEnvironmentEntries());
        verify(environmentEntriesListPresenter).setup(any(), eq(model.getEnvironmentEntries()), any());
    }

    @Test
    public void testAddNewEnvironmentEntry() {
        presenter.addNewEnvironmentEntry();
        verify(environmentEntriesListPresenter).add(itemObjectModelFactory.newItemObjectModel("",""));
        verify(presenter).fireChangeEvent();
    }
}