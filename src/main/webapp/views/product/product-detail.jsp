<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<jsp:include page="/views/decorators/header.jsp" />

<div class="row mt-4">
    <div class="col-md-5">
        <c:choose>
            <c:when test="${product.images.startsWith('http')}">
                <img src="${product.images}" class="img-fluid rounded border">
            </c:when>
            <c:otherwise>
                <img src="${pageContext.request.contextPath}/image?fname=${product.images}" class="img-fluid rounded border">
            </c:otherwise>
        </c:choose>
    </div>
    <div class="col-md-7">
        <h2>${product.productname}</h2>
        <p class="text-muted">Danh mục: <strong>${product.category.categoryname}</strong></p>
        <h3 class="text-danger"><fmt:formatNumber value="${product.price}" pattern="#,### VNĐ"/></h3>
        <p class="mt-3">${product.description}</p>
        <p><strong>Số lượng còn lại:</strong> ${product.quantity}</p>
        <p><strong>Trạng thái:</strong> ${product.status == 1 ? 'Còn hàng' : 'Hết hàng'}</p>
        <button class="btn btn-success btn-lg mt-3">Thêm vào giỏ hàng</button>
    </div>
</div>

<jsp:include page="/views/decorators/footer.jsp" />