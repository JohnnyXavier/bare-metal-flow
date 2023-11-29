package com.bmc.flow.modules.service.base;

import io.quarkus.panache.common.Page;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * A temporary class for paged results waiting for Q team to provide one on the framework
 * While simple, it is made "à la Spring Page" so it can be easily discarded once a -Paged Response- is available.
 *
 * @param <D> The Dto that will be at the core of the result.
 */
@Setter
@Getter
@RegisterForReflection
public class PageResult<D> {

    private List<D> resultSet;

    private Pagination pagination;

    public PageResult(final List<D> resultSet, final Long count, final Page page) {
        this.pagination = new Pagination(count, page);
        this.resultSet  = resultSet;
    }

    @Setter
    @Getter
    @RegisterForReflection
    private static class Pagination {

        private Long itemCount;

        private Long totalPages;

        private Integer currentPage;

        private Integer pageSize;

        public Pagination(final Long itemCount, final Page page) {
            this.itemCount   = itemCount;
            this.currentPage = page.index + 1;
            this.pageSize    = page.size;
            this.totalPages  = (itemCount / page.size) + ((itemCount % page.size) != 0 ? 1 : 0);
        }
    }
}
