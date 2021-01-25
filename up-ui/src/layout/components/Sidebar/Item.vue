<script>
    export default {
        name: 'MenuItem',
        functional: true,

        props: {
            icon: {
                type: String,
                default: ''
            },
            title: {
                type: String,
                default: ''
            },
            collapse:{
                type: Boolean,
                default: false
            }
        },
        render(h, context) {
            let {icon, title,collapse} = context.props
            icon='icon iconfont '+icon
            const vnodes = []
            if (icon) {
                if (!collapse){
                    vnodes.push(<i class= {icon} style="padding:0 3px 0 3px;font-size:14px;margin-left:-5px;"/>)
                }else{
                    vnodes.push(<i class= {icon} style="padding:0 3px 0 3px;margin-left:15px;font-size:14px"/>)
                }

            }
            if (title) {
                vnodes.push(<span slot='title'>{(title)}</span>)
            }
            return vnodes
        }
    }
</script>
