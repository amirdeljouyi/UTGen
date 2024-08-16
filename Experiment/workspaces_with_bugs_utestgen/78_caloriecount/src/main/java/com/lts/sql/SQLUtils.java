//  Copyright 2006, Clark N. Hobbie
//
//  This file is part of the util library.
//
//  The util library is free software; you can redistribute it and/or modify it
//  under the terms of the Lesser GNU General Public License as published by
//  the Free Software Foundation; either version 2.1 of the License, or (at
//  your option) any later version.
//
//  The util library is distributed in the hope that it will be useful, but
//  WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
//  or FITNESS FOR A PARTICULAR PURPOSE.  See the Lesser GNU General Public
//  License for more details.
//
//  You should have received a copy of the Lesser GNU General Public License
//  along with the util library; if not, write to the Free Software Foundation,
//  Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
//
package com.lts.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLUtils 
{
	public static void close (Connection connection, boolean printStackTrace)
	{
		if (null != connection)
		{
			try
			{
				connection.close();
			}
			catch (SQLException e)
			{
				if (printStackTrace)
					e.printStackTrace();
			}
		}
	}
	
	
	public static void close (Connection connection)
	{
		close(connection, false);
	}
	
	
	public static void close (Statement statement, boolean printStackTrace)
	{
		if (null != statement)
		{
			try
			{
				statement.close();
			}
			catch (SQLException e)
			{
				if (printStackTrace)
					e.printStackTrace();
			}
		}
	}
	
	public static void close (Statement statement)
	{
		close(statement, false);
	}
	
	
	public static void close (ResultSet rset, boolean printStackTrace)
	{
		if (null != rset)
		{
			try
			{
				rset.close();
			}
			catch (SQLException e)
			{
				if (printStackTrace)
					e.printStackTrace();
			}
		}
	}
	
	
	public static void close (ResultSet rset)
	{
		close(rset, false);
	}
	
	
	public static void close (
		Connection connection, 
		Statement statement, 
		ResultSet rset
	)
	{
		close(rset);
		close(statement);
		close(connection);
	}
	
	public static void close (Statement statement, ResultSet rset)
	{
		close(null, statement, rset);
	}
}
