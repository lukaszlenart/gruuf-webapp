<#include "/${parameters.templateDir}/bootstrap/control-close.ftl" />
<#include "/${parameters.templateDir}/simple/form-close.ftl" />
<script type="text/javascript">
    if (typeof jQuery != 'undefined') {
        if (typeof jQuery.fn.tooltip == 'function') {
            jQuery('i.s2b_tooltip').tooltip();
        }
    }
</script>

<#if parameters.focusElement??>
<script type="text/javascript">
    if (typeof jQuery != 'undefined') {
        jQuery(document).ready(function() {
            var element  = jQuery("#${parameters.focusElement?html}");
            if(element) {
                element.focus();
            }
        });
    }
</script>
</#if>
