/*
 * Copyright (C) 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.yushanginfo.hams.base.web.action.code

import net.yushanginfo.hams.code.model.*
import org.beangle.webmvc.support.action.RestfulAction

class InpatientStatusAction extends RestfulAction[InpatientStatus]

class StaffTypeAction extends RestfulAction[StaffType]

class CardTypeAction extends RestfulAction[CardType]

class FeeOriginAction extends RestfulAction[FeeOrigin]

class CriticalLevelAction extends RestfulAction[CriticalLevel]

class DiseaseTypeAction extends RestfulAction[DiseaseType]