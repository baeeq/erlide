/*******************************************************************************
 * Copyright (c) 2009 Vlad Dumitrescu and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available
 * at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Vlad Dumitrescu
 *******************************************************************************/
package org.erlide.backend.internal;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.xtext.xbase.lib.Pair;
import org.erlide.backend.api.ICodeBundle;
import org.erlide.util.ErlLogger;
import org.osgi.framework.Bundle;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class CodeBundle implements ICodeBundle {

    private final Map<String, CodeContext> paths;
    private final Collection<Pair<String, String>> inits;
    private final Bundle bundle;

    public CodeBundle(final Bundle bundle,
            final Map<String, CodeContext> paths2,
            final Collection<Pair<String, String>> inits) {
        this.bundle = bundle;
        paths = Maps.newHashMap(paths2);
        this.inits = inits;
    }

    @Override
    public Bundle getBundle() {
        return bundle;
    }

    @Override
    public Collection<String> getEbinDirs() {
        final List<String> result = Lists.newArrayList();
        for (final Entry<String, CodeContext> path : paths.entrySet()) {
            final Collection<String> myPath = BeamUtil.getPaths(path.getKey(),
                    bundle);
            if (myPath != null) {
                result.addAll(myPath);
            } else {
                ErlLogger.warn("Can't access path %s, "
                        + "plugin may be incorrectly built", path.getKey());
            }
        }
        return result;
    }

    @Override
    public Map<String, CodeContext> getPaths() {
        return Collections.unmodifiableMap(paths);
    }

    @Override
    public Collection<Pair<String, String>> getInits() {
        return Collections.unmodifiableCollection(inits);
    }

}
