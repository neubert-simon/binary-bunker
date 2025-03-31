import {useState} from "react";

/**
 * Custom hook for handling page pagination.
 *
 * @param {number} maxPageNumber The maximum number of pages allowed.
 * @param {number} [pageSteps=1] The step size for page changes.
 * @param {boolean} [usePrecisePageNumbers=false] If true, allows setting exact page numbers.
 * @returns {{page: number, handlePagePaginationClick: function, nextPage: function, prevPage: function}}
 * Returns the current page and functions to navigate pages.
 */
export function usePagePagination(maxPageNumber, pageSteps = 1, usePrecisePageNumbers = false) {
    const [page, setPage] = useState(1); // State to track the current page number

    /**
     * Handles page changes based on navigation rules.
     * If precise page numbers are used, sets the page to the desired number.
     * Otherwise, increments the page by the defined step size, looping back if needed.
     *
     * @param {number} [desiredPage=0] The exact page to navigate to (used when precise page numbers are enabled).
     */
    function handlePagePaginationClick(desiredPage = 0) {
        if(!usePrecisePageNumbers)
            setPage(page => page === maxPageNumber ? 1 : page + pageSteps);
        else
            setPage(desiredPage);
    }

    /**
     * Moves to the next page.
     * If the last page is reached, it loops back to the first page.
     */
    function nextPage() {
        setPage(page => {
            page++;

            if(page > maxPageNumber) return 1;

            return page;
        })
    }

    /**
     * Moves to the previous page.
     * If the first page is reached, it loops back to the last page.
     */
    function prevPage() {
        setPage(page => {
            page--;

            if(page <= 0) return maxPageNumber;

            return page;
        })
    }

    return {
        page,
        handlePagePaginationClick,
        nextPage,
        prevPage
    };
}