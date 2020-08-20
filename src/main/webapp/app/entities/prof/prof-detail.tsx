import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './prof.reducer';
import { IProf } from 'app/shared/model/prof.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProfDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ProfDetail = (props: IProfDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { profEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="intranetApp.prof.detail.title">Prof</Translate> [<b>{profEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="login">
              <Translate contentKey="intranetApp.prof.login">Login</Translate>
            </span>
          </dt>
          <dd>{profEntity.login}</dd>
          <dt>
            <span id="mdp">
              <Translate contentKey="intranetApp.prof.mdp">Mdp</Translate>
            </span>
          </dt>
          <dd>{profEntity.mdp}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="intranetApp.prof.email">Email</Translate>
            </span>
          </dt>
          <dd>{profEntity.email}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="intranetApp.prof.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{profEntity.nom}</dd>
          <dt>
            <span id="prenom">
              <Translate contentKey="intranetApp.prof.prenom">Prenom</Translate>
            </span>
          </dt>
          <dd>{profEntity.prenom}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="intranetApp.prof.type">Type</Translate>
            </span>
          </dt>
          <dd>{profEntity.type}</dd>
          <dt>
            <span id="tel">
              <Translate contentKey="intranetApp.prof.tel">Tel</Translate>
            </span>
          </dt>
          <dd>{profEntity.tel}</dd>
        </dl>
        <Button tag={Link} to="/prof" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/prof/${profEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ prof }: IRootState) => ({
  profEntity: prof.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProfDetail);
