<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<jsp:include page="/views/decorators/header.jsp" />

<c:set var="productList" value="${not empty latestProducts ? latestProducts : (not empty products ? products : (not empty listproduct ? listproduct : (not empty list ? list : items)))}" />

<div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 g-4">
    <c:forEach items="${productList}" var="p">
        <div class="col">
            <div class="card h-100 border-0 shadow-sm rounded-3 overflow-hidden">
                <div class="bg-light text-center position-relative" style="height: 200px;">
                    <c:choose>
                        <c:when test="${not empty p.images && p.images.startsWith('http')}">
                            <img src="${p.images}" class="card-img-top h-100 w-100 object-fit-cover" alt="${p.productname}">
                        </c:when>
                        <c:otherwise>
                            <img src="${pageContext.request.contextPath}/image?fname=${p.images}" class="card-img-top h-100 w-100 object-fit-cover" alt="${p.productname}">
                        </c:otherwise>
                    </c:choose>
                </div>

                <div class="card-body d-flex flex-column p-3">
                    <small class="text-muted mb-1">${p.category.categoryname}</small>
                    <h6 class="card-title fw-bold text-dark text-truncate mb-2" title="${p.productname}">${p.productname}</h6>

                    <div class="mt-auto d-flex justify-content-between align-items-center pt-2">
                        <span class="fw-bold text-danger">
                            <fmt:formatNumber value="${p.price}" pattern="#,### VNĐ"/>
                        </span>
                        <a href="${pageContext.request.contextPath}/product/detail?id=${p.productid}" class="btn btn-outline-primary btn-sm rounded-pill">
                            Xem chi tiết
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>

    <c:if test="${empty productList}">
        <div class="col-12 text-center py-5">
            <i class="bi bi-inbox fs-1 text-muted d-block mb-2"></i>
            <p class="text-secondary fw-semibold">Chưa có sản phẩm nào trong CSDL.</p>
        </div>
    </c:if>
</div>

<jsp:include page="/views/decorators/footer.jsp" />