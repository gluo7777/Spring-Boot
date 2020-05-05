// main client entry point
const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client')

class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = { blogs: [] };
    }

    componentDidMount() {
        client({ method: 'GET', path: '/api/blogs' }).done(response => {
            console.log({ response });
            this.setState({ blogs: response.entity._embedded.blogs })
        })
    }

    render() {
        return <BlogPane blogs={this.state.blogs} />
    }
}

class BlogPane extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            selected: this.props && this.props.blogs.length > 0 ? this.props.blogs[0] : null
        }
    }

    render() {
        // default blog first
        return (<div className="content">
            <select>
                {this.props.blogs.map(blog => <option key={blog._links.self.href} value={blog.id}>{blog.title}</option>)}
                <option key={this.props.blogs.length} value="addBlog">New Blog</option>
            </select>
            <div className="blog-heading">
                <input type="text" defaultValue={this.state.selected ? this.state.selected.title : ""} />
                <input type="datetime" defaultValue="today" disabled />
            </div>
            <textarea className="blog-content" placeholder="Start writing your blog..." rows="30" cols="20" defaultValue={this.state.selected ? this.state.selected.content : ""}>

            </textarea>
            <div className="blog-footer">
                <label htmlFor="tags">Tags</label><input type="text" id="tags" name="tags" defaultValue={this.state.selected ? this.state.selected.tags : ""} />
                <button>Delete</button>
                <button>Save</button>
            </div>
        </div>)
    }
}

ReactDOM.render(
    <App />,
    document.getElementById('content')
)