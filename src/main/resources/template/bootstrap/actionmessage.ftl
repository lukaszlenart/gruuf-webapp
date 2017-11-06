<#if (actionMessages?? && actionMessages?size > 0 && !parameters.isEmptyList)>
<div
    <#if parameters.id! != "">
        id="${parameters.id?html}"<#rt/>
    </#if>
    <#if parameters.cssClass??>
        class="alert alert-info alert-dismissible ${parameters.cssClass?html}"<#rt/>
    <#else>
        class="alert alert-info alert-dismissible"<#rt/>
    </#if>
    <#if parameters.cssStyle??>
        style="${parameters.cssStyle?html}"<#rt/>
    </#if>
>
  <button type="button" class="close" data-dismiss="alert">x</button>
    <#list actionMessages as message>
        <#if message! != "">
          <p><span class="ui-icon ui-icon-info" style="float: left; margin-right: 0.3em;"></span>
            <span><#if parameters.escape>${message!?html}<#else>${message!}</#if></span></p>
        </#if>
    </#list>
</div>
</#if>