/*----------------------------------------------------------------------------------------
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for license information.
 *---------------------------------------------------------------------------------------*/

package com.mycompany.app;

import com.google.common.collect.Multiset;
import com.google.common.collect.HashMultiset;

public class App 
{
    public static void main( String[] args )
    {
        Multiset<String> m = HashMultiset.create();


        System.out.println( "Hello Remote World!" );
    }
}
