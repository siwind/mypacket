/*
 * Copyright © 2016 Siwind, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.bupt.siwind.odl.impl;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.sal.binding.api.RpcProviderRegistry;
import org.opendaylight.controller.md.sal.binding.api.NotificationPublishService;

import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.PacketProcessingService;

import org.opendaylight.controller.sal.binding.api.BindingAwareBroker.RpcRegistration;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.mypacket.rev150105.MypacketService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MypacketProvider {

    private static final Logger LOG = LoggerFactory.getLogger(MypacketProvider.class);

    //md-sal service provider
    private final DataBroker dataBroker;
    private final RpcProviderRegistry rpcRegistry;
    private final NotificationPublishService notificationService;

    //MypacketService 
    private RpcRegistration<MypacketService> mypacketService = null;

    public MypacketProvider(final DataBroker dataBroker, 
                final RpcProviderRegistry rpcRegistry,
                final NotificationPublishService notificationService) {

        this.dataBroker = dataBroker;
        this.rpcRegistry = rpcRegistry;
        this.notificationService = notificationService;
    }

   
    /**
     * Method called when the blueprint container is created.
     */
    public void init() {
        LOG.info("MypacketProvider Session Initiated");
        PacketProcessingService packetProcessingService = rpcRegistry.getRpcService(PacketProcessingService.class);
        
        mypacketService = rpcRegistry.addRpcImplementation(MypacketService.class, new MypacketImpl(packetProcessingService));
    }

    /**
     * Method called when the blueprint container is destroyed.
     */
    public void close() {
        LOG.info("MypacketProvider Closed");
        
        if (mypacketService != null) {
            mypacketService.close();
        }
    }
}


