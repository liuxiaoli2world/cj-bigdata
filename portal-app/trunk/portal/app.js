import * as React from 'react';
import { Router } from 'react-router';
import {hashHistory, resizeEvent} from 'util/carousel-helper';
import routes from './routes';

const unlisten = hashHistory.listen(location => {
  window.dispatchEvent(resizeEvent);
});

export default (
	
    <Router history={hashHistory} routes={routes}/>
);

