package com.bmc.flow.modules.database.repositories;

import com.bmc.flow.modules.database.entities.UserEntity;
import io.quarkus.hibernate.reactive.panache.PanacheQuery;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public class UserRepository implements PanacheRepositoryBase<UserEntity, UUID> {

  private static final String SELECT_DTO = " select e.id, e.email, e.callSign, e.avatar, e.isActive, e.createdAt," +
      " e.seniority.id, e.department.id";

  private static final String FROM_ENTITY = " from UserEntity e";

  /**
   * TODO: have hibernate generate the native query...
   * update: tested 20K calls(no cache), native query takes total 23.275 while this jpa with multi-join takes 25.589
   * 10% more throughput (and faster)
   * <p>
   *
   * <pre>
   *  select cast(e.id as varchar), e.first_name, e.last_name, e.email, e.call_sign, e.avatar, e.is_active, e.created_at,
   *  cast(e.seniority_id as varchar)
   *  from users e
   *  join user_project up on e.id = up.user_id
   *  where up.project_id = :projectId
   * </pre>
   *
   *
   * <pre>
   * Hibernate:
   *     select
   *         userentity0_.id as col_0_0_,
   *         userentity0_.first_name as col_1_0_,
   *         userentity0_.last_name as col_2_0_,
   *         userentity0_.email as col_3_0_,
   *         userentity0_.call_sign as col_4_0_,
   *         userentity0_.avatar as col_5_0_,
   *         userentity0_.is_active as col_6_0_,
   *         userentity0_.created_at as col_7_0_,
   *         userentity0_.seniority_id as col_8_0_
   *     from
   *         public.users userentity0_
   *     left outer join
   *         public.users_projects projects1_
   *             on userentity0_.id=projects1_.user_id
   *     left outer join
   *         public.project projectent2_
   *             on projects1_.project_id=projectent2_.id
   *     where
   *         projectent2_.id=$1
   * </pre>
   */
  public PanacheQuery<UserEntity> findAllByCollectionId(final String collectionName, final UUID collectionId, final Sort sort) {
    return this.find(String.format(SELECT_DTO + FROM_ENTITY +
            " left join e.%s as collection" +
            " where collection.id =?1", collectionName),
        sort, collectionId);
  }

}
