/*
 * Copyright (C) 2015 Drakeet <drakeet.me@gmail.com>
 *
 * This file is part of Meizhi
 *
 * Meizhi is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Meizhi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Meizhi.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.veaer.gank.request;

/**
 * Created by Veaer on 15/9/8.
 */
public class LineFactory {
    static Line sSingleton = null;

    public static Line getSingleton() {
        synchronized (LineFactory.class) {
            if (sSingleton == null) {
                sSingleton = new LineRetrofit().getService();
            }
            return sSingleton;
        }
    }
}
