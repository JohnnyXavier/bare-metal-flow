package com.bmc.flow.modules.resources.base;

import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import lombok.Getter;

import java.util.Optional;
import java.util.function.ToIntFunction;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * this class is a wrapper for sorting and paging information coming from requests.
 */
@Getter
public class Pageable {

    /**
     * maximum amount of records that can be requested per page
     */
    private static final Integer                MAX_PAGE_SIZE = 100;
    /**
     * minimum amount of records that can be requested per page
     */
    private static final Integer                MIN_PAGE_SIZE = 5;
    /**
     * a pageSize has to be between {@link Pageable#MIN_PAGE_SIZE} and {@link Pageable#MAX_PAGE_SIZE}
     */
    private final        ToIntFunction<Integer> checkPageSize = (pageSize) -> max(min(pageSize, MAX_PAGE_SIZE), MIN_PAGE_SIZE);
    private final        Sort                   sort;
    private final        Sort.Direction         direction;
    private final        Page                   page;

    public Pageable(final String sortBy, final String sortDir, final Integer pageIx, final Integer pageSize) {
        this.direction = Optional.ofNullable(sortDir)
                                 .filter("desc"::equals)
                                 .map(dir -> Sort.Direction.Descending)
                                 .orElse(Sort.Direction.Ascending);

        this.sort = Sort.by(sortBy, direction);

        this.page = Optional.ofNullable(pageIx)
                            .map(index -> Page.of(max(index, 0), checkPageSize.applyAsInt(pageSize)))
                            .orElseGet(() -> Page.ofSize(pageSize));
    }
}
