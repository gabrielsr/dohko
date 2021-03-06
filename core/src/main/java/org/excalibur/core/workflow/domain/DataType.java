/**
 *     Copyright (C) 2013-2014  the original author or authors.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License,
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package org.excalibur.core.workflow.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "data-type")
@XmlEnum(String.class)
public enum DataType
{
    @XmlEnumValue("I")
    INPUT('I'),

    @XmlEnumValue("O")
    OUTPUT('O');

    private final char id;

    private DataType(char id)
    {
        this.id = id;
    }

    /**
     * @return the id
     */
    public Character getId()
    {
        return id;
    }

    public static DataType valueOf(char id)
    {
        for (DataType type : DataType.values())
        {
            if (type.getId().equals(id))
            {
                return type;
            }
        }
        return null;
    }
}
