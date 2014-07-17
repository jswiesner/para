/*
 * Copyright 2013-2014 Erudika. http://erudika.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * For issues and patches go to: https://github.com/erudika
 */
package com.erudika.para.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;

/**
 * The core domain interface. All Para objects implement it.
 * @author Alex Bogdanovski [alex@erudika.com]
 * @see PObject
 */
public interface ParaObject extends Serializable, Linkable, Votable {

	/**
	 * The id of an object. Usually an autogenerated unique string of numbers.
	 * @return the id
	 */
	String getId();

	/**
	 * Sets a new id. Must not be null or empty.
	 * @param id the new id
	 */
	void setId(String id);

	/**
	 * The name of the object. Can be anything.
	 * @return the name. default: [type id]
	 */
	String getName();

	/**
	 * Sets a new name. Must not be null or empty.
	 * @param name the new name
	 */
	void setName(String name);

	/**
	 * The application name. Added to support multiple separate apps.
	 * Every object must belong to an app.
	 * @return the app id (name). default: para
	 */
	String getAppid();

	/**
	 * Sets a new app name. Must not be null or empty.
	 * @param appid the new app id (name)
	 */
	void setAppid(String appid);

	/**
	 * The id of the parent object.
	 * @return the id of the parent or null
	 */
	String getParentid();

	/**
	 * Sets a new parent id. Must not be null or empty.
	 * @param parentid a new id
	 */
	void setParentid(String parentid);

	/**
	 * The name of the object's class. This is equivalent to {@link Class#getSimpleName()}.toLowerCase()
	 * @return the simple name of the class
	 */
	String getType();

	/**
	 * Sets a new object type. Must not be null or empty.
	 * @param type a new type
	 */
	void setType(String type);

	/**
	 * The id of the user who created this. Should point to a {@link User} id.
	 * @return the id or null
	 */
	String getCreatorid();

	/**
	 * Sets a new creator id. Must not be null or empty.
	 * @param creatorid a new id
	 */
	void setCreatorid(String creatorid);

	/**
	 * The plural name of the object. For example: user - users
	 * @return the plural name
	 */
	String getPlural();

	/**
	 * The URI of this object. For example: /users/123
	 * @return the URI
	 * @see com.erudika.para.utils.Utils#getObjectURI(com.erudika.para.core.ParaObject, boolean, boolean)
	 */
	String getObjectURI();

	/**
	 * The user object of the creator.
	 * @return the user who created this or null if {@link #getCreatorid()} is null
	 * @see User
	 */
	@JsonIgnore
	ParaObject getCreator();

	/**
	 * The parent object..
	 * @return the parent or null if {@link #getParentid()} is null
	 */
	@JsonIgnore
	ParaObject getParent();

	/**
	 * The time when the object was created, in milliseconds.
	 * @return the timestamp of creation
	 */
	Long getTimestamp();

	/**
	 * Sets the timestamp.
	 * @param timestamp a new timestamp in milliseconds.
	 */
	void setTimestamp(Long timestamp);

	/**
	 * The last time this object was updated. Timestamp in ms.
	 * @return timestamp in milliseconds
	 */
	Long getUpdated();

	/**
	 * Sets the last updated timestamp.
	 * @param updated a new timestamp
	 */
	void setUpdated(Long updated);

	/**
	 * The tags associated with this object. Tags must not be null or empty.
	 * @return a set of tags, or an empty set
	 * @see Tag
	 */
	List<String> getTags();

	/**
	 * Merges the given tags with existing tags.
	 * @param tags the additional tags, or clears all tags if set to null
	 */
	void setTags(List<String> tags);

	/**
	 * Stores this object in the data store.
	 * @return the id of the object (a new id if object is new)
	 * @see com.erudika.para.annotations.Stored
	 * @see com.erudika.para.persistence.DAO#create(com.erudika.para.core.ParaObject)
	 */
	String create();

	/**
	 * Updates the object permanently. Changes to {@link com.erudika.para.annotations.Locked} fields are ignored.
	 * @see com.erudika.para.annotations.Locked
	 * @see com.erudika.para.persistence.DAO#update(com.erudika.para.core.ParaObject)
	 */
	void update();

	/**
	 * Deletes the object permanently by removing it from the data store.
	 * @see com.erudika.para.persistence.DAO#delete(com.erudika.para.core.ParaObject)
	 */
	void delete();

	/**
	 * Checks if an object is stored in the data store.
	 * @return true if the object is persisted to the data store.
	 * @see com.erudika.para.persistence.DAO#read(java.lang.String)
	 */
	boolean exists();

	/**
	 * This is the key which controls the location of the object in a sharded space.
	 * It is used mainly for routing with Elasticsearch.
	 * @return the id value (default) or some other value like appid
	 */
	String getShardKey();

	/**
	 * Sets the shard key.
	 * @param key some value (usually appid)
	 */
	void setShardKey(String key);

}
