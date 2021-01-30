package com.felipesantacruz.productmanager.util.pagination;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class PaginationLinksUtils
{
	private StringBuilder linkHeader;
	private Page<?> page;
	private UriComponentsBuilder uriBuilder;

	public String createLinkHeader(Page<?> page, UriComponentsBuilder uriBuilder)
	{
		setFields(page, uriBuilder);

		if (page.hasNext())
		{
			String uri = constructUriForPage(page.getNumber() + 1);
			linkHeader.append(buildLinkHeader(uri, "next"));
		}

		if (page.hasPrevious())
		{
			String uri = constructUriForPage(page.getNumber() - 1);
			appendCommaIfNecessary();
			linkHeader.append(buildLinkHeader(uri, "prev"));
		}

		if (!page.isFirst())
		{
			String uri = constructUriForPage(0);
			appendCommaIfNecessary();
			linkHeader.append(buildLinkHeader(uri, "first"));
		}

		if (!page.isLast())
		{
			String uri = constructUriForPage(page.getTotalPages() - 1);
			appendCommaIfNecessary();
			linkHeader.append(buildLinkHeader(uri, "last"));
		}

		return linkHeader.toString();
	}

	private void setFields(Page<?> page, UriComponentsBuilder uriBuilder)
	{
		this.page = page;
		linkHeader = new StringBuilder();
		this.uriBuilder = uriBuilder;
	}

	private String constructUriForPage(int newPageNumber)
	{
		return uriBuilder.replaceQueryParam("page", newPageNumber).replaceQueryParam("size", page.getSize()).build().encode()
				.toUriString();
	}

	private String buildLinkHeader(final String uri, final String rel)
	{
		return "<" + uri + ">; rel=\"" + rel + "\"";
	}

	private void appendCommaIfNecessary()
	{
		if (linkHeader.length() > 0)
			linkHeader.append(", ");
	}
}
