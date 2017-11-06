<#if (actionErrors?? && actionErrors?size > 0 && !parameters.isEmptyList)>
<div
    <#if parameters.id?if_exists != "">
        id="${parameters.id?html}"<#rt/>
    </#if>
    <#if parameters.cssClass??>
        class="alert alert-danger alert-dismissible ${parameters.cssClass?html}"<#rt/>
    <#else>
        class="alert alert-danger alert-dismissible"<#rt/>
    </#if>
    <#if parameters.cssStyle??>
        style="${parameters.cssStyle?html}"<#rt/>
    </#if>
>
  <button type="button" class="close" data-dismiss="alert">x</button>
    <#list actionErrors as message>
        <#if message?if_exists != "">
          <p><#if parameters.escape>${message!?html}<#else>${message!}</#if></p>
        </#if>
    </#list>
</div>
</#if>