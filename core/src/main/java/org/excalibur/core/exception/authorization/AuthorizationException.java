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
package org.excalibur.core.exception.authorization;

public class AuthorizationException extends RuntimeException
{
    /**
     * Serial code version <code>serialVersionUID</code> for serialization.
     */
    private static final long serialVersionUID = -8242797113901375982L;

    public AuthorizationException()
    {
        super();
    }

    public AuthorizationException(String message)
    {
        super(message);
    }

    public AuthorizationException(Throwable cause)
    {
        super(cause);
    }

    public AuthorizationException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
