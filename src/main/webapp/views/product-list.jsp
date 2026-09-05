<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<h3 class="mb-4">Danh Sách Sản Phẩm</h3>
<div class="row row-cols-1 row-cols-md-3 g-4">
    <c:forEach items="${products}" var="p">
        <div class="col">
            <div class="card h-100 shadow-sm">
                <c:choose>
                    <c:when test="${p.images.startsWith('http')}">
                        <img src="${p.images}" class="card-img-top" height="200" style="object-fit:cover;">
                    </c:when>
                    <c:otherwise>
                        <img src="${pageContext.request.contextPath}/image?fname=${p.images}" class="card-img-top" height="200" style="object-fit:cover;">
                    </c:otherwise>
                </c:choose>
                <div class="card-body">
                    <h5 class="card-title">${p.productName}</h5>
                    <p class="text-danger fs-5 fw-bold"><fmt:formatNumber value="${p.price}" pattern="#,### VNĐ"/></p>
                    <a href="${pageContext.request.contextPath}/product/detail?id=${p.productId}" class="btn btn-primary w-100">Xem chi tiết</a>
                </div>
            </div>
        </div>
    </c:forEach>
</div>

<!-- Phân trang 6 sản phẩm/trang -->
<nav class="mt-4">
    <ul class="pagination justify-content-center">
        <c:forEach begin="1" end="${totalPages}" var="i">
            <li class="page-item ${currentPage == i ? 'active' : ''}">
                <a class="page-link" href="${pageContext.request.contextPath}/product?page=${i}">${i}</a>
            </li>
        </c:forEach>
    </ul>
</nav>