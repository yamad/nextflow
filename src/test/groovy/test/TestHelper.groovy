/*
 * Copyright (c) 2013-2014, Centre for Genomic Regulation (CRG).
 * Copyright (c) 2013-2014, Paolo Di Tommaso and the respective authors.
 *
 *   This file is part of 'Nextflow'.
 *
 *   Nextflow is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   Nextflow is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with Nextflow.  If not, see <http://www.gnu.org/licenses/>.
 */

package test

/**
 *
 * @author Paolo Di Tommaso <paolo.ditommaso@gmail.com>
 */
class TestHelper {

    static <T> T proxyFor(Class<T> clazz) {

        def holder = [:]
        def proxy = {}.asType(clazz)
        proxy.metaClass.getProperty = { name -> holder.get(name) }
        proxy.metaClass.setProperty = { name, value -> holder.put(name,value) }
        proxy.metaClass.invokeMethod = { String name, Object args ->
            if( name.startsWith('set') && name.size()>3 && args?.size()==1 ) {
                def key = name.substring(3,4).toLowerCase()
                if( name.size()>4 ) key += name.substring(4)
                holder.put(key,args[0])
            }
            else if( name.startsWith('get') && name.size()>3 && args?.size()==0 ) {
                def key = name.substring(3,4).toLowerCase()
                if( name.size()>4 ) key += name.substring(4)
                holder.get(key)
            }

        }

        return proxy
    }

}